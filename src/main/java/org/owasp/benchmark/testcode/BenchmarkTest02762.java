package org.owasp.benchmark.testcode;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.owasp.esapi.codecs.Base64;

@WebServlet(value = "/deserialization-00/BenchmarkTest02762")
public class BenchmarkTest02762 extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String param = "";
        if (request.getHeader("BenchmarkTest02762") != null) {
            param = request.getHeader("BenchmarkTest02762");
        }

        param = java.net.URLDecoder.decode(param, "UTF-8");
        ObjectInputStream bar = doSomething(param);

        try {
            Object result = bar.readObject();
            bar.close();
            org.owasp.benchmark.helpers.DeserializationHelper.outputDeserializationResults(
                    result, response);

        } catch (ClassNotFoundException e) {
            response.getWriter().println("The entered class was not found.");
        } catch (IOException e) {
            response.getWriter().println("ReadObject Error. / Invalid stream input");
        } catch (NullPointerException e) {
            response.getWriter().println("It is also safe! / Stream is null.");
        } catch (RuntimeException e) {
            response.getWriter().println("Looks like you made it.");
        }
    }

    private static ObjectInputStream doSomething(String param) throws IOException {

        String bar = "";
        if (param != null) {
            java.util.List<String> valuesList = new java.util.ArrayList<String>();
            valuesList.add("safe");
            valuesList.add(param);
            valuesList.add("moresafe");

            valuesList.remove(0); // remove the 1st safe value
            bar = valuesList.get(0); // get the param value
        }

        byte[] serializedObject = Base64.decode(bar);
        ByteArrayInputStream bais = new ByteArrayInputStream(serializedObject);
        ObjectInputStream bis = new ObjectInputStream(bais);

        return bis;
    }
}
