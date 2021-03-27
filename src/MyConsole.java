import models.InputObject;
import TableManager.Table;
import IOManager.InputOrder.ConvertObject;
import IOManager.JSON.JSON;
import models.JSONObject;
import java.util.Scanner;

class MyConsole {
    JSON json = new JSON();

    public MyConsole(){
    }

    public void start () {
        Scanner scanner = new Scanner(System.in).useDelimiter("done");
        String string = scanner.next();

        if (string.equals("end")){
            System.exit(0);
        }

        JSONObject jsonObject = json.parserJSON(string);

        if (jsonObject != null) {
            ConvertObject convertObject = new ConvertObject(jsonObject);
            InputObject inputObject = convertObject.convert();
            if (inputObject == null){
            }else {
                Table table = new Table(inputObject);
                table.handle();
            }
        }
        start();
    }
}
