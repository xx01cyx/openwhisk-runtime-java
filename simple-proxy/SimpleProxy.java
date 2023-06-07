import java.io.BufferedReader;
import java.nio.DSMInputStream;
import java.nio.DSMOutputStream;
import java.nio.charset.StandardCharsets;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class SimpleProxy {

    private HttpServer server;

    public SimpleProxy(int port) throws IOException {
        this.server = HttpServer.create(new InetSocketAddress(port), -1);

        this.server.createContext("/run", new RunHandler());
        this.server.setExecutor(null); // creates a default executor
    }

    public void start() {
        server.start();
    }

    private class RunHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            boolean useDSM = true;

            if (useDSM) {
                // System.out.println("000");
                DSMInputStream dis = new DSMInputStream(t.getRequestBody());
                // System.out.println("111");
                JsonObject x = (JsonObject) dis.readDSM();
                // System.out.println("222");
                long start = System.currentTimeMillis();
                JsonPrimitive end = x.getAsJsonPrimitive("end");
                System.out.println("get end as json primitive");
                System.out.println("end is " + end.getAsNumber());
                long diff = start - end.getAsNumber().longValue();
                System.out.println("diff is " + diff);
                // JsonPrimitive diff = x.getAsJsonPrimitive("diff");
                // System.out.println("get diff as json primitive");
                // System.out.println("diff is " + diff.getAsString());
                // System.out.println(x.toString());
                System.out.println("333");
                dis.close();
                System.out.println("444");

                String message = "yeah";
                DSMOutputStream dos = new DSMOutputStream(t.getResponseBody(), false, false);
                System.out.println("555");
                dos.writeDSM(message);
                System.out.println("666");
                dos.close();
            } else {
                System.out.println("GET REQUEST!");
                InputStream is = t.getRequestBody();
                JsonParser parser = new JsonParser();
                JsonElement ie = parser.parse(new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8)));
                long start = System.currentTimeMillis();
                JsonObject x = ie.getAsJsonObject();
                JsonPrimitive end = x.getAsJsonPrimitive("end");
                System.out.println("end is " + end.getAsNumber());
                long diff = start - end.getAsNumber().longValue();
                System.out.println("diff is " + diff);

                String response = String.valueOf(diff);
                byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
                t.sendResponseHeaders(200, bytes.length);
                OutputStream os = t.getResponseBody();
                os.write(bytes);
                os.close();
            }
        }
    }

    public static void main(String args[]) throws Exception {
        try {
            SimpleProxy proxy = new SimpleProxy(8080);
            proxy.start();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
