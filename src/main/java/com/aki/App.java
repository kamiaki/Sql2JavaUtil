import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class App {
    //如果要转成java字符串, 将格式化的sql文件复制粘贴在E盘的sql.txt中
    private static String sqlFile = "C:\\Users\\170725e\\Desktop\\sql.txt";
    //如果要将字符串转成sql语句, 将所有"sb.append(" ... ")包裹的sql语句都复制到java.txt中"
    private static String javaFile = "C:\\Users\\170725e\\Desktop\\java.txt";

    public static void main(String[] args)throws Exception {
        //true为不覆盖之前的文件,false为覆盖
//        sql2Java("sb",sqlFile,javaFile,true);
        sql2Java("sql",sqlFile,javaFile,false);
//        java2Sql("sb",javaFile,sqlFile,true);
//        java2Sql("sql",javaFile,sqlFile,false);
    }


    /**
     * 将格式化后的sql文件,或普通的txt文件,读取每一行sql语句,用"sb.append("")来包裹"
     * @param var       stringbuffer的变量
     * @param sqlPath   sql文件的路径
     * @param javaFile  java文件的路径
     * @param isAppend  是否在原文件后拼接
     * @throws Exception
     */
    public static void sql2Java(String var,String sqlPath,String javaFile,boolean isAppend)throws Exception{
        BufferedReader br = new BufferedReader(new FileReader(sqlPath));
        BufferedWriter bw = new BufferedWriter(new FileWriter(javaFile,isAppend));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = format.format(new Date());
        String str = "";
        if (isAppend){
            bw.newLine();
            bw.write("--------------------"+date+"-------------------------");
            bw.newLine();
        }
        while((str = br.readLine()) != null) {
            if (!str.trim().equals("")){
                str = str.trim();
                int i = -1;
                //去掉注释
                if ((i = str.indexOf("-- ")) != -1){
                    str = str.substring(0,i);
                }
                if (!str.trim().equals("")){
                    bw.write(" "+var+".append(\" " + str.trim() + " \");");
                    bw.newLine();
                }
            }
        }
        bw.close();
        br.close();
        System.out.println("java文件输出ok");
    }

    /**
     * 将java代码中被"sb.append("")来包裹的sql文件,还原成原来的sql
     * @param var       stringbuffer的变量名
     * @param javaPath  java文件的路径
     * @param sqlPath   sql文件的路径
     * @param isAppend  是否在原文件后拼接
     * @throws Exception
     */
    public static void java2Sql(String var,String javaPath,String sqlPath,boolean isAppend)throws Exception{
        BufferedReader br = new BufferedReader(new FileReader(javaPath));
        BufferedWriter bw = new BufferedWriter(new FileWriter(sqlPath));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = format.format(new Date());
        String str = "";
        if (isAppend){
            bw.newLine();
            bw.write("--------------------"+date+"-------------------------");
            bw.newLine();
        }
        while((str = br.readLine()) != null) {
            bw.write(str.trim().replace(var+".append(\"","").replace("\");",""));
            bw.newLine();
        }
        bw.flush();
        bw.close();
        br.close();
        System.out.println("sql文件输出ok");
    }

}
