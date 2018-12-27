package limited.it.planet.visicoolertracking.util;

/**
 * Created by Planet IT
 */

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;

import limited.it.planet.visicoolertracking.database.LocalStorageDB;

public class CsvFileExport {
    Context context;
    LocalStorageDB localStorageDB;

    public CsvFileExport(Context ct) {
        this.context = ct;
        localStorageDB = new LocalStorageDB(context);

    }




    public void csvFileExport() {
        localStorageDB.open();
        Cursor c = null;

        try {

            c = localStorageDB.selectALlRecords();


            int rowcount = 0;

            int colcount = 0;


            File sdCardDir = Environment.getExternalStorageDirectory();

            String filename = "visicooler.csv";

            // the name of the file to export with

            File saveFile = new File(sdCardDir, filename);


            FileWriter fw = new FileWriter(saveFile);

            BufferedWriter bw = new BufferedWriter(fw);

            rowcount = c.getCount();

            colcount = c.getColumnCount();

            if (rowcount > 0) {

                c.moveToFirst();

                for (int i = 0; i < colcount; i++) {

                    if (i != colcount - 1) {

                        bw.write(c.getColumnName(i) + ",");

                    } else {

                        bw.write(c.getColumnName(i));

                    }

                }

                bw.newLine();

                for (int i = 0; i < rowcount; i++) {

                    c.moveToPosition(i);

                    for (int j = 0; j < colcount; j++) {

                        if (j != colcount - 1)

                            bw.write(c.getString(j) + ",");

                        else

                            bw.write(c.getString(j));

                    }

                    bw.newLine();

                }

                bw.flush();




                Toast.makeText(context,"Database Exported Successfully in CSV file format ", Toast.LENGTH_LONG).show();



            }

        } catch (Exception ex) {

            localStorageDB.close();
            Toast.makeText(context,"Failed to export in csv file format \n"+ex, Toast.LENGTH_LONG).show();

        } finally {

            localStorageDB.close();
        }
    }
}
