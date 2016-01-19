/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package particle_track;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author sa01ta
 */
public class IOUtils {
    public static double[][] setupStartLocs(String location, String habitat, String basedir)
    {
        double startlocs[][] = new double[10][3];
        // FYNE configurations
        if (location.equalsIgnoreCase("fyne"))
        {
            System.out.println("****** Loch Fyne tracking ******");
            startlocs = setupStartLocationsFyneFarms();
        }
        // North Minch fish farm locations
        else if (location.equalsIgnoreCase("minch") || location.equalsIgnoreCase("minch_continuous"))
        {
            String sitedir=basedir+"minch_sites/";
            if (habitat.equalsIgnoreCase("coast"))
            {
                startlocs = IOUtils.readFileDoubleArray(sitedir+"minchcoast_1km_utm2.dat",3892,4," ",true);
            }
            else if (habitat.equalsIgnoreCase("fishfarm"))
            {
                //startlocs = readFileDoubleArray(sitedir+"140124_ms_site_utm_area.dat",122,4," ",true); 
                startlocs = IOUtils.readFileDoubleArray(sitedir+"150508_ms_site_utm_FMA.dat",122,4," ",true);
            }
            else if (habitat.equalsIgnoreCase("fishfarm2"))
            {
                startlocs = IOUtils.readFileDoubleArray(sitedir+"151130_fishfarm_locs.dat",195,4," ",true);
            }
            else if (habitat.equalsIgnoreCase("fishfarm3"))
            {
                //startlocs = readFileDoubleArray(sitedir+"140124_ms_site_utm_area.dat",122,4," ",true); 
                startlocs = IOUtils.readFileDoubleArray(sitedir+"160119_fishfarms_jun15.dat",205,5," ",true);
            }
            else if (habitat.equalsIgnoreCase("nephrops"))
            {
                startlocs = IOUtils.readFileDoubleArray(sitedir+"sublittoralmud_utm.dat",5896,4," ",true);
            }
            else if (habitat.equalsIgnoreCase("spill"))
            {
                startlocs = IOUtils.readFileDoubleArray(sitedir+"150408_spill_utm_10vary.dat",16,4," ",true);
            }
            else if (habitat.equalsIgnoreCase("test"))
            {
                startlocs = IOUtils.readFileDoubleArray(sitedir+"151123_test_locations.dat",5,4," ",true);
            }
            else
            {
                System.out.println("****** North Minch tracking ******");
                //startlocs = readFileDoubleArray("D:\\"+datadir+"\\site_xy.dat",17,3," ",true);
                //startlocs = readFileDoubleArray("C:\\Users\\sa01ta\\Documents\\Sealice_NorthMinch\\farms_utm2.dat",213,3," ",true);
                startlocs = IOUtils.readFileDoubleArray("C:\\Users\\sa01ta\\Documents\\Sealice_NorthMinch\\site_locations\\140124_ms_site_utm_area.dat",122,4," ",true);
            }     
        }
        // Firth of Lorn boundary locations 
        // boundary_940pt in article submitted to Ecography (coastal sites c1km apart).
        // others (_with_interest or _with_search) include offshore renewable energy sites
        // (as in article submitted to Jornal of Applied Ecology).
        else
        {
            startlocs = IOUtils.readFileDoubleArray(basedir+"lorncoast\\boundary_940pt.dat",940,3," ",true);
            //double startlocs[][] = readFileDoubleArray(basedir+"lorncoast\\boundary_with_interest.dat",966,3," ",true);
            //startlocs = readFileDoubleArray(basedir+"lorncoast\\boundary_with_search.dat",1252,3," ",true);
            //startlocs = readFileDoubleArray("C:\\Users\\sa01ta\\Documents\\lorn\\120903_renewableimpact\\131107_revision\\startlocs_array10.dat",971,3," ",true);
            //startlocs = readFileDoubleArray("C:\\Users\\sa01ta\\Documents\\lorn\\120903_renewableimpact\\131107_revision\\startlocs_array25.dat",1018,3," ",true);
            //startlocs = readFileDoubleArray("C:\\Users\\sa01ta\\Documents\\lorn\\120903_renewableimpact\\131107_revision\\startlocs_array50.dat",1095,3," ",true);
            //startlocs = readFileDoubleArray(basedir+"infrastructure\\portsmarinas_num.txt",41,3," ",true);
            //startlocs = readFileDoubleArray(basedir+"infrastructure\\portsmarinas_search.dat",353,3," ",true);    
        }
        return startlocs;
    }
    
    public static double[][] setupOpenBCLocs(String location, String habitat, String basedir)
    {
        double open_BC_locs[][] = new double[10][3];
        if (location.equalsIgnoreCase("minch") || location.equalsIgnoreCase("minch_continuous"))
        {
            String sitedir=basedir+"minch_sites/";
            open_BC_locs = IOUtils.readFileDoubleArray(sitedir+"open_boundary_locs.dat",120,3," ",true);
        } 
        else
        {
            open_BC_locs = IOUtils.readFileDoubleArray("C:\\Users\\sa01ta\\Documents\\lorn\\120903_renewableimpact\\131107_revision\\open_boundary_locs.dat",93,3," ",true);
        }
        return open_BC_locs;
    }
    
    public static double[][] setupStartLocationsFyneFarms()
    {
        int nsites = 9;
        double startlocs[][]= new double[nsites][3];

        // S->N ordering (reordering sites from original nonsensical order based on:
        // order_SN=[9 7 4 6 5 8 3 1 2];)
        
        startlocs[0][0]=1; startlocs[0][1]=352401; startlocs[0][2]=6190933;
        startlocs[1][0]=2; startlocs[1][1]=354969; startlocs[1][2]=6193169;
        startlocs[2][0]=3; startlocs[2][1]=354246; startlocs[2][2]=6194759;
        startlocs[3][0]=4; startlocs[3][1]=348880; startlocs[3][2]=6199380;
        startlocs[4][0]=5; startlocs[4][1]=352745; startlocs[4][2]=6201735;
        startlocs[5][0]=6; startlocs[5][1]=348606; startlocs[5][2]=6204475;
        startlocs[6][0]=7; startlocs[6][1]=353078; startlocs[6][2]=6206339;
        startlocs[7][0]=8; startlocs[7][1]=357420; startlocs[7][2]=6217200; 
        startlocs[8][0]=9; startlocs[8][1]=361834; startlocs[8][2]=6223063;
        
        // "hypothetical" fishfarm used for SAMS newsletter
        //fishfarms(1,1)=351000;
        //fishfarms(1,2)=6195000;
        return startlocs;
    }
    
    public static double[][] setupStartLocationsSpill(double[][] uvnode, int startelem)
    {
        int nsites = 1;
        double startlocs[][]= new double[nsites][3];
        startlocs[0][0] = 1; startlocs[0][1] = uvnode[startelem][0]; startlocs[0][2] = uvnode[startelem][1];
        return startlocs;
    }
    
    
    public static double[][] readFileDoubleArray(String filename, int rows, int cols, String sep, boolean note)
    {
        double[][] myDouble = new double[rows][cols];
        int x=0, y=0;
        boolean failed = false;
        double sum = 0;
        try
        {
            BufferedReader in = new BufferedReader(new FileReader(filename));	//reading files in specified directory
 
            String line;
            
            outer: while ((line = in.readLine()) != null)	//file reading
            {
                y=0;
                if (x >= rows)
                {
                    System.out.println(filename+" has more rows than expected.");
                    failed = true;
                    break;
                }
                String[] values = line.split(" ");
                for (String str : values)
                {
                    if (y >= cols)
                    {
                        System.out.println(filename+" has more columns than expected: "+y);
                        failed = true;
                        break outer;
                    }
                    double str_double = Double.parseDouble(str);
                    myDouble[x][y]=str_double;
                    sum+=myDouble[x][y];
                    //System.out.print(myDouble[x][y] + " ");
                    y++;

                }

                //System.out.println("");
                x++;
            }
            in.close();
            
        } catch( IOException ioException ) {
            throw new RuntimeException(ioException);
            //System.err.println("******************* Cannot read from file "+filename+" ******************************");
            //failed = true;
        }
        if (note == true && failed == false)
        {
            System.out.printf("Created %dx%d array from file: %s\n",myDouble.length,myDouble[0].length,filename);
            System.out.println("Array sum at read time = "+sum);
        }
        else if (failed == true)
        {
            System.out.println("FAILED to read file "+filename);
            System.exit(1);
        }
        return myDouble;
    }
    
    public static int[][] readFileIntArray(String filename, int rows, int cols, String sep, boolean note)
    {
        int[][] myInt = new int[rows][cols];
        int x=0, y=0;
        boolean failed = false;
        try
        {
            BufferedReader in = new BufferedReader(new FileReader(filename));	//reading files in specified directory
            //System.out.println("in readFileIntArray");
            String line;
            while ((line = in.readLine()) != null)	//file reading
            {
                
                y=0;
                if (x >= rows)
                {
                    System.out.println(filename+" has more rows than expected.");
                    failed = true;
                    break;
                }
                String[] values = line.split(sep);
                for (String str : values)
                {
                    if (y >= cols)
                    {
                        System.out.println(filename+" has more columns than expected.");
                        failed = true;
                        break;
                    }
                    int str_int = Integer.parseInt(str);
                    myInt[x][y]=str_int;
                    //System.out.print(myDouble[x][y] + " ");
                    y++;

                }
                //System.out.println("");
                x++;
            }
            in.close();
            
        
        } catch( Exception e ) {
            System.err.println("******************* Cannot read from file "+filename+" ******************************");
            failed = true;
        }
        if (note == true && failed == false)
        {
            System.out.printf("Created %dx%d array from file: %s\n",myInt.length,myInt[0].length,filename);
        }
        else if (failed == true)
        {
            System.out.println("FAILED to read file "+filename);
            System.exit(1);
        }
        return myInt;
    }
    
    public static void writeDoubleArrayToFile(double[][] variable, String filename)
    {
        try
        {
            // Create file 
            FileWriter fstream = new FileWriter(filename);
            PrintWriter out = new PrintWriter(fstream);
            for (int i = 0; i < variable.length; i++)
            {
                for (int j = 0; j < variable[0].length; j++)
                {
                    out.printf("%.4e ",variable[i][j]);
                }
                out.printf("\n");
            }
            //Close the output stream
            out.close();
        }catch (Exception e){//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }
    public static void writeIntegerArrayToFile(int[][] variable, String filename)
    {
        try
        {
            // Create file 
            FileWriter fstream = new FileWriter(filename);
            PrintWriter out = new PrintWriter(fstream);
            for (int i = 0; i < variable.length; i++)
            {
                for (int j = 0; j < variable[0].length; j++)
                {
                    out.printf("%d ",variable[i][j]);
                }
                out.printf("\n");
            }
            //Close the output stream
            out.close();
        }catch (Exception e){//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }
    public static void particleLocsToFile(Particle[] particles, int nparts, int tt, String filename)
    {
        try
        {
            // Create file 
            FileWriter fstream = new FileWriter(filename,true);
            PrintWriter out = new PrintWriter(fstream);
            for (int i = 0; i < particles.length/nparts; i++)
            {
                out.printf("%d %d %f %f %d\n",tt,i,particles[i].getLocation()[0],particles[i].getLocation()[1],particles[i].getElem());
            }
            //Close the output stream
            out.close();
        }catch (Exception e){//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    // Print ALL particlelocations at a particular time, with corresponding start locations
    public static void particleLocsToFile1(Particle[] particles, String filename)
    {
        try
        {
            // Create file 
            FileWriter fstream = new FileWriter(filename,true);
            PrintWriter out = new PrintWriter(fstream);
            for (int i = 0; i < particles.length; i++)
            {
                out.printf("%d %f %f %f $f\n",i,particles[i].getStartLocation()[0],particles[i].getStartLocation()[1],particles[i].getLocation()[0],particles[i].getLocation()[1]);
            }
            //Close the output stream
            out.close();
        }catch (Exception e){//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    public static void particleLocsToFile2(double time, Particle[] particles, int i, String filename)
    {
        try
        {
            // Create file 
            FileWriter fstream = new FileWriter(filename,true);
            PrintWriter out = new PrintWriter(fstream);
            out.printf("%f %f %f %d\n",time,particles[i].getLocation()[0],particles[i].getLocation()[1],particles[i].getElem());
            //Close the output stream
            out.close();
        }catch (Exception e){//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }
}