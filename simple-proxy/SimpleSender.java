import java.io.BufferedReader;
import java.nio.DSMInputStream;
import java.nio.DSMOutputStream;
import java.io.OutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

public class SimpleSender 
{
    public static void main( String[] args )
    {
        boolean useDSM = true;

        JsonObject.class.getName();
        JsonArray.class.getName();

        try {
            JsonObject json = new JsonObject();
            JsonArray arr = new JsonArray();
            for (int i = 0; i < 10000; i++) {
                JsonObject val = new JsonObject();
                val.addProperty("value", i);
                arr.add(val);
            }
            json.add("ret", arr);
            // System.out.println("constructed json object: " + json.toString());

            URL nextContainer = new URL("http://simple-proxy:8080/run");
            HttpURLConnection connection = (HttpURLConnection) nextContainer.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            if (useDSM) {
                DSMOutputStream dos = new DSMOutputStream(connection.getOutputStream(), false, false);
                json.addProperty("end", System.currentTimeMillis());
                dos.writeDSM(json);
                dos.close();
                System.out.println("333");

                DSMInputStream responseDis = new DSMInputStream(connection.getInputStream());
                System.out.println("444");
                Object response = responseDis.readDSM();
                System.out.println("sender get response: " + response.toString());
            } else {
                DataOutputStream wr = new DataOutputStream (connection.getOutputStream());
                json.addProperty("end", System.currentTimeMillis());
                wr.writeBytes(json.toString());
                wr.flush();
                wr.close();
                System.out.println("http request sent");

                InputStream nextIs = connection.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(nextIs));
                StringBuffer response = new StringBuffer(); // or StringBuffer if Java version 5+
                String line;
                while ((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }
                rd.close();
                System.out.println("get response: " + line);
            }


        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
