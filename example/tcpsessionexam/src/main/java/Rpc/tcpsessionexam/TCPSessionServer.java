package Rpc.tcpsessionexam;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;

import com.ocean.rpc.common.RpcContext;
import com.ocean.rpc.common.RpcFilter;
import com.ocean.rpc.io.ByteBufferStream;
import com.ocean.rpc.server.RpcService;
import com.ocean.rpc.server.RpcTcpServer;

public class TCPSessionServer {
    static class Session {
        final static IdentityHashMap<RpcContext, Integer> sidMap = new IdentityHashMap<RpcContext, Integer>();
        final static ArrayList<HashMap<String, Object>> sessions = new ArrayList<HashMap<String, Object>>();
        public static HashMap<String, Object> getSession(RpcContext context) {
            return sessions.get(sidMap.get(context));
        }
    }

    static class MyServerFilter implements RpcFilter {

        @Override
        public ByteBuffer inputFilter(ByteBuffer istream, RpcContext context) {
            int len = istream.limit() - 7;
            if (len > 0 &&
                istream.get() == 's' &&
                istream.get() == 'i' &&
                istream.get() == 'd') {
                int sid = ((int)istream.get()) << 24 |
                          ((int)istream.get()) << 16 |
                          ((int)istream.get()) << 8  |
                           (int)istream.get();
                Session.sidMap.put(context, sid);
                return istream.slice();
            }
            int sid = Session.sessions.size();
            Session.sidMap.put(context, sid);
            Session.sessions.add(new HashMap<String, Object>());
            istream.rewind();
            return istream;
        }

        @Override
        public ByteBuffer outputFilter(ByteBuffer ostream, RpcContext context) {
            int sid = Session.sidMap.get(context);
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
    }

    public static int inc() {
        HashMap<String, Object> session = Session.getSession(RpcService.getCurrentContext());
        if (!session.containsKey("n")) {
            session.put("n", 0);
            return 0;
        }
        int i = (Integer)session.get("n") + 1;
        session.put("n", i);
        return i;
    }
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        RpcTcpServer server = new RpcTcpServer("tcp://localhost:4321");
        server.setFilter(new MyServerFilter());
        server.setDebugEnabled(true);
        server.add("inc", TCPSessionServer.class);
        server.start();
        System.out.println("START");
        System.in.read();
        server.stop();
        System.out.println("STOP");
    }
}
