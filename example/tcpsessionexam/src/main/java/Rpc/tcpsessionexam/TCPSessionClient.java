package Rpc.tcpsessionexam;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.IdentityHashMap;

import com.ocean.rpc.client.ClientContext;
import com.ocean.rpc.client.RpcClient;
import com.ocean.rpc.client.RpcTcpClient;
import com.ocean.rpc.common.RpcContext;
import com.ocean.rpc.common.RpcFilter;
import com.ocean.rpc.io.ByteBufferStream;

public class TCPSessionClient {
    static class MyClientFilter implements RpcFilter {
        private final IdentityHashMap<RpcClient, Integer> sidMap = new IdentityHashMap<RpcClient, Integer>();
        @Override
        public ByteBuffer inputFilter(ByteBuffer istream, RpcContext context) {
            RpcClient client = ((ClientContext)context).getClient();
            int len = istream.limit() - 7;
            if (len > 0 &&
                istream.get() == 's' &&
                istream.get() == 'i' &&
                istream.get() == 'd') {
                int sid = ((int)istream.get()) << 24 |
                          ((int)istream.get()) << 16 |
                          ((int)istream.get()) << 8  |
                           (int)istream.get();
                sidMap.put(client, sid);
                return istream.slice();
            }
            istream.rewind();
            return istream;
        }

        @Override
        public ByteBuffer outputFilter(ByteBuffer ostream, RpcContext context) {
            RpcClient client = ((ClientContext)context).getClient();
            if (sidMap.containsKey(client)) {
                int sid = sidMap.get(client);
                ByteBuffer buf = ByteBufferStream.allocate(ostream.remaining() + 7);
                buf.put((byte)'s');
                buf.put((byte)'i');
                buf.put((byte)'d');
                buf.put((byte)(sid >> 24 & 0xff));
                buf.put((byte)(sid >> 16 & 0xff));
                buf.put((byte)(sid >> 8 & 0xff));
                buf.put((byte)(sid & 0xff));
                buf.put(ostream);
                ByteBufferStream.free(ostream);
                return buf;
            }
            return ostream;
        }
    }

    public interface Stub {
        int inc();
    }
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        System.out.println("START");
        RpcTcpClient client = new RpcTcpClient("tcp://localhost:4321");
        client.setFilter(new MyClientFilter());
        Stub stub = client.useService(Stub.class);
        System.out.println(stub.inc());
        System.out.println(stub.inc());
        System.out.println(stub.inc());
        System.out.println(stub.inc());
        System.out.println("STOP");
    }
}
