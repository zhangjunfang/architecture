package Rpc.exam.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ocean.rpc.server.HttpContext;
import com.ocean.rpc.server.RpcHttpMethods;
import com.ocean.rpc.server.RpcHttpService;

public class MyRpcServlet2 extends HttpServlet {
    
	private static final long serialVersionUID = 5290243329802442261L;
	private final RpcHttpService service = new RpcHttpService();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        Exam2 exam2 = new Exam2();
        RpcHttpMethods methods = new RpcHttpMethods();
        methods.addInstanceMethods(exam2);
        methods.addInstanceMethods(exam2, Exam1.class);
        service.handle(new HttpContext(request,
                                      response,
                       this.getServletConfig(),
                     this.getServletContext()),
                                      methods);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
}