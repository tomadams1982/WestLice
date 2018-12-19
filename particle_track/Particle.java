/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package particle_track;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author tomdude
 */
public class Particle {
    
    final private int id;
    // horizontal position
    private double[] xy = new double[2];
    final private double[] startLoc = new double[2];
    private String startSiteID = "0";
    
    private String coordRef;
    
    private ISO_datestr startDate;
    private double startTime = 0;
    
    private int elem;
    private double[][] nrList = new double[5][2];
    private double[][] cornerList = new double[3][2];
    // release time
    private double releaseTime = 0;
    // vertical position
    private double z = 0;
    private int depLayer = 0;
    // settlement details
    private double age = 0;
    private int status = 0;
    private double density = 1;
    private double mortRate = 0.01; // default hourly rate, based on results of Stein et al. 2005 (copepodid, nauplii rate marginally lower = 0.0078)
    private boolean arrived = false;
    private boolean viable = false;
    private boolean free = false;
    private boolean settledThisHour = false;
    private boolean boundaryExit = false;
    
    private String lastArrival = "0";
    
    // A list to store data on the arrivals made by each particle.
    // If rp.endOnArrival=true, this will contain a maximum of one element.
    // --- Not presently used ---
    private List<Arrival> arrivals;
    
    // create a new particle at a defined location, at the water surface
    public Particle(double xstart, double ystart, double startDepth, String startSiteID, int id, double mortalityRate, String coordRef)
    {
        this.id = id;
        this.xy[0] = xstart;
        this.xy[1] = ystart;
        this.startSiteID = startSiteID;
        this.startLoc[0] = xstart;
        this.startLoc[1] = ystart;
        this.mortRate = mortalityRate;
        this.z = startDepth; 
        
        this.arrivals = new ArrayList<>();
        
        this.coordRef = coordRef;
    }
    public Particle(double xstart, double ystart, double startDepth, String startSiteID, int id, double mortalityRate, 
            ISO_datestr startDate, double startTime, String coordRef)
    {
        this.id = id;
        this.xy[0] = xstart;
        this.xy[1] = ystart;
        this.startSiteID = startSiteID;
        this.startDate = startDate;
        this.startTime = startTime;
        this.startLoc[0] = xstart;
        this.startLoc[1] = ystart;
        this.mortRate = mortalityRate;
        
        this.coordRef = coordRef;
        
        //this.arrivals = new ArrayList<Arrival>();
    }

//    public void setReleaseScenario(int releaseScenario, double[][] startlocs)
//    {
//        switch (releaseScenario) {
//            // integer to switch release scenario
//            // 0 all at time zero
//            // 1 tidal release (evenly over first 24 hours)
//            // 2 continuous release (1 per hour per site)
//            // 3 continuous release (5 per hour per site)
//            // 4 continuous release (10 per hour per site)
//            // 5 continuous release (20 per hour per site)
//            // 10 defined release times
//            case 0:
//                this.setReleaseTime(0);
//                break;
//            case 1:
//                this.setReleaseTime((this.id / startlocs.length) % 25);
//                break;
//            case 2:
//                this.setReleaseTime(Math.floor(this.id / startlocs.length));
//                break;
//            case 3:
//                this.setReleaseTime(Math.floor(this.id / (5 * startlocs.length)));
//                break;
//            case 4:
//                this.setReleaseTime(Math.floor(this.id / (10 * startlocs.length)));
//                break;
//            case 5:
//                this.setReleaseTime(Math.floor(this.id / (20 * startlocs.length)));
//                break;
//            case 10:
//                this.setReleaseTime(startlocs[this.id][3]);
//                break;
//        }
//    }
    
    @Override
    public String toString()
    {
        return this.getID()+" "+this.xy.toString();
    }
    
    // Not presently used
    public void reportArrival(int sourceLocation, int arrivalLocation, double arrivalTime, double arrivalDensity)
    {
        arrivals.add(new Arrival(sourceLocation,arrivalLocation,arrivalTime,arrivalDensity));
        //System.out.printf("Arrival (particle %d): %d %d %f %f\n", this.getID(),sourceLocation,arrivalLocation,arrivalTime,arrivalDensity);
    }
    public List<Arrival> getArrivals()
    {
        return this.arrivals;
    }
    
    public void setReleaseTime(double releaseTime)
    {
        this.releaseTime = releaseTime;
    }
    public double getReleaseTime()
    {
        return this.releaseTime;
    }
    public int getID()
    {
        return this.id;
    }
    public double[] getLocation()
    {
        return this.xy;
    }
    public double[] getStartLocation()
    {
        return this.startLoc;
    }
    public String getStartID()
    {
        return this.startSiteID;
    }
    public ISO_datestr getStartDate()
    {
        return this.startDate;
    }
    public double getStartTime()
    {
        return this.startTime;
    }
    public void setLocation(double x, double y)
    {
        this.xy[0] = x;
        this.xy[1] = y;
    }
    public void setElem(int elem)
    {
        this.elem = elem;
    }
    public int getElem()
    {
        return this.elem;
    }
//    public void setNrList(float[][] uvnode)
//    {
//        this.nrList = nearestCentroidList(this.xy[0], this.xy[1], uvnode);
//    }
    public double[][] getNrList()
    {
        return this.nrList;
    }
    public void setCornerList(int elemPart, int[][] trinodes, double[][] nodexy)
    {
        this.cornerList[0][0]=nodexy[trinodes[elemPart][0]][0];
        this.cornerList[0][1]=nodexy[trinodes[elemPart][0]][1];
        this.cornerList[1][0]=nodexy[trinodes[elemPart][1]][0];
        this.cornerList[1][1]=nodexy[trinodes[elemPart][1]][1];
        this.cornerList[2][0]=nodexy[trinodes[elemPart][2]][0];
        this.cornerList[2][1]=nodexy[trinodes[elemPart][2]][1];
    }
    public double[][] getCornerList()
    {
        return this.cornerList;
    }
    
    public double getZ()
    {
        return this.z;
    }
    public void setZ(double z)
    {
        this.z = z;
    }
    public int getDepthLayer()
    {
        return this.depLayer;
    }
    public void setDepthLayer(int dep)
    {
        this.depLayer = dep;
    }
    // see lower down for setMortRate (based on salinity)
    public double getMortRate()
    {
        return mortRate;
    }
    public void setDensity()
    {
        this.density = this.density*(1-this.mortRate);
        //System.out.println("density = "+this.density);
    }
    public double getDensity()
    {
        return this.density;
    }
    public void setStatus(int status)
    {
        // 0 = not free
        // 1 = free
        // 2 = viable (able to settle)
        // 3 = settled
        // 66 = boundary exit
        // 666 = dead (exceeded lifespan)
        this.status=status;
    }
    public int getStatus()
    {
        return this.status;
    }
    
    /** Set particle depth in the water column based on its defined behaviour and
     *  the time
     * 1 - passive, stay on surface
     * 2 - passive, stay on bottom (layer 10)
     * 3 - passive, stay in mid layer (layer 5)
     * 4 - vertical swimming: surface for hours 19-6, mid layer (5) hours 7-18
     * 5 - rapid drop (1->10) at hour 6, then gradually move back up
     * 6 - top during flood tides, mid during ebb (local)
     * 7 - mid during flood tides, bed during ebb (local)
     * 8 - top during flood tides, bed during ebb (local)
     * MORE...? "homing" ability
     */
    public void setDepthLayer(int behaviour, String tideState)
    {
        boolean reducedFile = true;
        switch (behaviour)
        {
            case 1: this.depLayer = 0; break;
            case 2: this.depLayer = 9; break;
            case 3: this.depLayer = 5; break;
            case 6: 
                if (tideState.equalsIgnoreCase("flood"))
                {
                    this.depLayer = 0;
                } else
                {
                    this.depLayer = 5;
                }
                break;
            case 7: 
                if (tideState.equalsIgnoreCase("flood"))
                {
                    this.depLayer = 5;
                } else
                {
                    this.depLayer = 9;
                }
                break;
            case 8: 
                if (tideState.equalsIgnoreCase("flood"))
                {
                    this.depLayer = 0;
                } else
                {
                    this.depLayer = 9;
                }
                break;
        }
        // enable running file with two depth layers ("top"=0 and "bottom"=1)
        if (reducedFile==true)
        {
            if (this.depLayer>0)
            {
                this.depLayer=1;
            }
        }
        //return 0;
    }
   
    /** put particle in the correct depth layer, based upon
     * its z position, and
     * 
     * @param localDepth  element bathymetry value
     * @param layers 
     */
    public void setLayerFromDepth(double localDepth, float[] layers)
    {
        int depNearest = 0;
        double dZmin = 1000;
        for (int i = 0; i < layers.length; i++)
        {
            if (Math.abs(this.z - localDepth*layers[i]) < dZmin)
            {
                depNearest = i;
            }
        }
        //System.out.printf("setting depth layer: %d (%f, particle depth = %f)\n",depNearest,localDepth*layers[depNearest],this.z);
        this.setDepthLayer(depNearest);
    }
    
    public double verticalDiffusion()
    {
        double vertDiff = 0;
        return vertDiff;
    }
    
    
    public void setViable(boolean viable)
    {
        this.viable = viable;
    }
    public void setArrived(boolean arrived)
    {
        this.arrived = arrived;
    }
    public void setLastArrival(String loc)
    {
        this.lastArrival = loc;
    }
    public String getLastArrival()
    {
        return this.lastArrival;
    }
    public void setSettledThisHour(boolean settled)
    {
        this.settledThisHour = settled;
    }
    public void setFree(boolean free)
    {
        this.free = free;
    } 
    public void setBoundaryExit(boolean exit)
    {
        this.boundaryExit = exit;
    }
    public boolean getViable()
    {
        return this.viable;
    }
    public boolean getArrived()
    {
        return this.arrived;
    }
    public boolean getSettledThisHour()
    {
        return this.settledThisHour;
    }
    public boolean getFree()
    {
        return this.free;
    }
    public boolean getBoundaryExit()
    {
        return this.boundaryExit;
    }    
    public double[] behaveVelocity(int behaviour)
    {
        double[] uv = new double[2];
        return uv;
    }
    
    public double[] smagorinskyDiffusionVelocity(int node, int[] neighbours, double u, double v, double[][] uvnode)
    {
        double[] uv = new double[2];
        return uv;
    }
    
    /**
     * Compute salinity from an average of the corner nodes of the containing element.
     * @param tt
     * @param salinity
     * @param trinodes
     * @return 
     */
    public double salinity(int tt, double[][] salinity, int[][] trinodes)
    {
        double s = 0;
        double dist1 = distanceEuclid(this.xy[0],this.xy[1],cornerList[0][0],cornerList[0][1]);
        double dist2 = distanceEuclid(this.xy[0],this.xy[1],cornerList[1][0],cornerList[1][1]);
        double dist3 = distanceEuclid(this.xy[0],this.xy[1],cornerList[2][0],cornerList[2][1]);
        double weight1 = 1.0/(dist1*dist1);
        double weight2 = 1.0/(dist2*dist2);
        double weight3 = 1.0/(dist3*dist3);
        double weightSum = weight1+weight2+weight3;
        
        s = (1.0/weightSum)*(weight1*salinity[tt][trinodes[0][elem]]+weight2*salinity[tt][trinodes[1][elem]]+weight3*salinity[tt][trinodes[2][elem]]);
        
        return s;
    }
    /**
     * Sets the mortality rate for the particle packet based upon local salinity
     * @param salinity
     * @return 
     */
    public void setMortRate(double salinity)
    {
        // estimated 2nd order polynomial fit to Bricknell et al.'s (2006) data
        this.mortRate = 0.0011*salinity*salinity - 0.07*salinity + 1.1439;
        //System.out.println("salinity = "+salinity+" mortrate = "+this.mortRate);
    }
     
    public void increaseAge(double increment)
    {
        this.age+=increment;
    }
    public double getAge()
    {
        return this.age;
    }
    
    public void setDepth(double D_hVert, double sinkingRateMean, double sinkingRateStd, double dt, double localDepth)
    {
        double depthNew = this.z;
        
        // Do some stuff with the sinking and diffusion parameters here
        // Simple example here PRESENTLY UNTESTED, and enforces a uniform distribution
        depthNew += dt * (sinkingRateMean + sinkingRateStd*ThreadLocalRandom.current().nextDouble(-1.0,1.0));
        depthNew += dt * D_hVert * ThreadLocalRandom.current().nextDouble(-1.0,1.0);
        
        // Add reading of vertical water velocity?
        
        if (depthNew > 0)
        {
            depthNew = 0;
        }
        if (depthNew < localDepth)
        {
            depthNew = localDepth;
        }
        
        this.z = depthNew;
    }
    
    
    
    // --------------------------------------------------------------------------------------------------------------
    // Everything to do with velocity calculation below here
    // --------------------------------------------------------------------------------------------------------------
    /**
     * Find the nearest mesh element centroid
     * @param x
     * @param y
     * @param uvnode
     * @return 
     */
    public static int nearestCentroid(double x, double y, float[][] uvnode)
    {
        int nearest = -1;
        double dist=10000000;
    
        for (int i = 0; i < uvnode.length; i++)
        {
            double distnew = Math.sqrt((x-uvnode[0][i])*(x-uvnode[0][i])+(y-uvnode[1][i])*(y-uvnode[1][i]));
            if (distnew<dist)
            {
                dist=distnew;
                nearest=i;
            }
        }
        //System.out.printf("In Particle.nearestCentroid "+nearest+"\n");
        return nearest;
    }
    /**
     * Make a list of nearest mesh element centroids
     * @param x
     * @param y
     * @param uvnode
     * @return 
     */
    public static double[][] nearestCentroidList(double x, double y, float[][] uvnode)
    {
        double[][] nearestList = new double[5][2];
        int nearest = -1;
        double dist=10000000;
    
        for (int i = 0; i < uvnode.length; i++)
        {
            double distnew = Math.sqrt((x-uvnode[0][i])*(x-uvnode[0][i])+(y-uvnode[1][i])*(y-uvnode[1][i]));
            if (distnew<dist)
            {
                dist=distnew;
                nearest=i;
                // Shift everything along one element
                nearestList[4][0]=nearestList[3][0];
                nearestList[4][1]=nearestList[3][1];
                nearestList[3][0]=nearestList[2][0];
                nearestList[3][1]=nearestList[2][1];
                nearestList[2][0]=nearestList[1][0];
                nearestList[2][1]=nearestList[1][1];             
                nearestList[1][0]=nearestList[0][0];
                nearestList[1][1]=nearestList[0][1];

                nearestList[0][0]=i;
                nearestList[0][1]=dist;              
            }
        }
        return nearestList;
    }
    

    
    /**
     * 
     * @param nrList
     * @param tt
     * @param u
     * @param v
     * @param numLayers
     * @param depLayer
     * @return 
     */
    public static double[] velocityFromNearestList(double[][] nrList, int tt, float u[][][], float v[][][], int depLayer)
    {
        double[] velocity = new double[2];
        double[] weights = new double[nrList.length];
        double usum=0,vsum=0,sum=0;
        for (int i = 0; i < nrList.length; i++)
        {
            if (nrList[i][1]!=0)
            {
                weights[i]=1.0/(nrList[i][1]*nrList[i][1]);
            }
            else
            {
                weights[i]=1;
            }
            
            //System.out.printf("tt %d i %d",tt,i);
//            System.out.printf(" --- elem %d dist %.4f weight %.4e --- vel = %.4f %.4f\n",
//                (int)nrList[i][0],nrList[i][1],weights[i],u[tt][depLayer][(int)nrList[i][0]],v[tt][depLayer][(int)nrList[i][0]]);
            
            usum=usum+weights[i]*u[tt][depLayer][(int)nrList[i][0]];
            vsum=vsum+weights[i]*v[tt][depLayer][(int)nrList[i][0]];
//            usum=usum+weights[i]*u[tt][(int)nrList[i][0]*numLayers+depLayer];
//            vsum=vsum+weights[i]*v[tt][(int)nrList[i][0]*numLayers+depLayer];
            sum=sum+weights[i];
        }
        usum=usum/sum;
        vsum=vsum/sum;
        velocity[0]=usum;
        velocity[1]=vsum;
        //System.out.printf("Interpolated Velocity = %.4f %.4f\n",velocity[0],velocity[1]);
        return velocity;
    }
        
    /**
     * Euclidean distance between two points
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return 
     */
    public static double distanceEuclid(double x1, double y1, double x2, double y2)
    {
        double dist = Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
        return dist;
    }
    
    /**
     * Compute the Euclidean distance (in metres) between two points.
     * If the supplied points are WGS84 coordinates, convert their x,y separation 
     * to metres prior to calculating the Euclidean distance.
     * 
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param coordRef
     * @return 
     */
    public static double distanceEuclid2(double x1, double y1, double x2, double y2, String coordRef)
    {
        double dx = x1-x2;
        double dy = y1-y2;
        
        double[] distXY = new double[]{dx,dy};
        
        if (coordRef.equalsIgnoreCase("WGS84"))
        {
            distXY = ParallelParticleMover.distanceDegreesToMetres(distXY,new double[]{x1,y2});
        }
                
        double dist = Math.sqrt(distXY[0]*distXY[0] + distXY[1]*distXY[1]);
        return dist;
    }
    
    
    
    /**
     * Search through lists of elements progressively further away from the last known
     * location in order to find the new location.
     * 
     * Returned array contains the element in which the particle is determined to be located, 
     * plus the number of counts required at each scale (for diagnostics).
     * @param newlocx
     * @param newlocy
     * @param elemPart
     * @param nodexy
     * @param trinodes
     * @param neighbours
     * @param allelems
     * @return 
     */
    public static int[] findContainingElement(double[] xy, int elemPart,
            float[][] nodexy, int[][] trinodes, int[][] neighbours, int[] allelems)
    {
        int[] c = new int[6];
        int[] elems = new int[1];
        elems[0] = elemPart;
        //System.out.println("findContainingElement: elems[0]="+elems[0]);
        int whereami=whichElement(xy,elems,nodexy,trinodes);
        //System.out.println("findContainingElement: whereami="+whereami);
        c[1]=1;
        if (whereami==-1)
        {
            //int[] elems0 = neighbours[elemPart];
            c[2]=1;
            whereami=whichElement(xy,new int[]{neighbours[0][elemPart],neighbours[1][elemPart],neighbours[2][elemPart]},nodexy,trinodes);
            // if fails, look in nearest 10 (id numerical)
            if (whereami==-1)
            {
                c[3]=1;
                int[] elems1 = new int[10];
                for (int j = 0; j < 10; j++)
                {
                    elems1[j] = Math.min(Math.max(elemPart-5+j,0),allelems.length-1);
                }
                whereami=whichElement(xy,elems1,nodexy,trinodes);
                // if fails, look in nearest 500 (id numerical)
                if (whereami==-1)
                {
                    c[4]=1;
                    int[] elems2 = new int[500];
                    for (int j = 0; j < 500; j++)
                    {
                        elems2[j] = Math.min(Math.max(elemPart-250+j,0),allelems.length-1);
                    }
                    whereami=whichElement(xy,elems2,nodexy,trinodes);
                    // if this fails, look in all elements
                    if (whereami==-1)
                    {
                        c[5]=1;
                        whereami=whichElement(xy,allelems,nodexy,trinodes);
                    }
                }
            }
        }
        c[0]=whereami;
        //System.out.printf("%d %d %d %d %d %d\n",c[0],c[1],c[2],c[3],c[4],c[5]);
//        if (c[0] == 0 || c[0] == -1)
//        {
//            System.out.println("Element out of bounds, fixing location");
//            System.out.printf("whereami=0 --- %.6e %.6e %d\n",newlocx,newlocy,elemPart);
//        }
        return c;
    }
    
    /**
     * Find which element a particle resides within (edge checking)
     * @param xy
     * @param elems
     * @param nodexy
     * @param trinodes
     * @return 
     */
    public static int whichElement(double[] xy, int[] elems, float[][] nodexy, int[][] trinodes)
    {        
        int which = -1;
        int res = 0;
        for (int i = 0; i < elems.length; i++)
        {
            double[] xt = new double[3];
            double[] yt = new double[3];
            
            for (int j = 0; j < 3; j++)
            {
                try
                {
                    xt[j]=nodexy[0][trinodes[j][elems[i]]];
                    yt[j]=nodexy[1][trinodes[j][elems[i]]];
                }
                catch (Exception e)
                {
                    System.err.println(i+" "+j+" "+elems[i]+" "+trinodes[j][elems[i]]+" "+nodexy[0][trinodes[j][elems[i]]]);
                }
            }
            // check whether (x,y) lies within this
            //fprintf('check %d\n', possibleElems(i));
            
            double f1 = (xy[1]-yt[0])*(xt[1]-xt[0]) - (xy[0]-xt[0])*(yt[1]-yt[0]);
            double f2 = (xy[1]-yt[2])*(xt[0]-xt[2]) - (xy[0]-xt[2])*(yt[0]-yt[2]);
            double f3 = (xy[1]-yt[1])*(xt[2]-xt[1]) - (xy[0]-xt[1])*(yt[2]-yt[1]);
            if(f1*f3 >= 0.0 && f3*f2 >= 0.0) 
            {
                res = 1;
            }
            if(res==1)
            {
                which=elems[i];
                break;
            }
        }
        //System.out.printf("whichElement: particle in %d\n", which);
        return which;
    }
    
    /**
     * Calculate a list of neighbour cells based on a specified (e.g. particle) location
     * (0: containing element, 1-3: neighbours of the containing element), and calculate
     * the Euclidean distances.
     * 
     * @param xy
     * @param elemPart0
     * @param neighbours
     * @param uvnode
     * @param nodexy
     * @param trinodes
     * @param allelems
     * @param coordRef
     * @return 
     */
    public static double[][] neighbourCellsList(double[] xy, int elemPart0, 
            int[][] neighbours, float[][] uvnode, float[][] nodexy, int[][] trinodes, int[] allelems, String coordRef)
    {      
        double[][] nrList = new double[5][2];
        // distance to elem
        //int elem = nearestCentroid(this.xy[0],this.xy[1],uvnode);
        int elem[] = findContainingElement(xy, elemPart0,
            nodexy, trinodes, neighbours, allelems);
        // If particle is not within the mesh (value returned by findContainingElement = -1)
        // exit this method returning array of zeros.
        if (elem[0] == -1)
        {
            return nrList;
        }
        int thisElem = elem[0]; 
        //int elem = this.elem;
        nrList[0][0] = thisElem;
        nrList[0][1] = distanceEuclid2(xy[0],xy[1],uvnode[0][thisElem],uvnode[1][thisElem],coordRef);
        // distance to neighbouring elems
        nrList[1][0] = neighbours[0][thisElem];
        nrList[1][1] = distanceEuclid2(xy[0],xy[1],uvnode[0][neighbours[0][thisElem]],uvnode[1][neighbours[0][thisElem]],coordRef);   
        nrList[2][0] = neighbours[1][thisElem];
        nrList[2][1] = distanceEuclid2(xy[0],xy[1],uvnode[0][neighbours[1][thisElem]],uvnode[1][neighbours[1][thisElem]],coordRef);    
        nrList[3][0] = neighbours[2][thisElem];
        nrList[3][1] = distanceEuclid2(xy[0],xy[1],uvnode[0][neighbours[2][thisElem]],uvnode[1][neighbours[2][thisElem]],coordRef);      
        nrList[4][0] = 0;
        nrList[4][1] = 1000000; 
        
//        System.out.printf("NeighbourCells:\n"
//                + "0: %d %f; 1: %d %f; 2: %d %f; 3: %d %f\n",
//                (int)nrList[0][0],nrList[0][1],(int)nrList[1][0],nrList[1][1],(int)nrList[2][0],nrList[2][1],(int)nrList[3][0],nrList[3][1]);
        
        return nrList;
    }
    
    /**
     * Update particle location using an RK4 integration step.
     * 
     * @param u
     * @param v
     * @param neighbours
     * @param uvnode
     * @param nodexy
     * @param trinodes
     * @param allelems
     * @param tt
     * @param st
     * @param dt
     * @param stepsPerStep
     * @param coordRef
     * @return 
     */
    public double[] rk4Step(float u[][][], float v[][][], // velocities
            int[][] neighbours, float[][] uvnode, float[][] nodexy, 
            int[][] trinodes, int[] allelems,      // other mesh info
            int tt, int st, double dt,                                  // locate particle in space and time
            int stepsPerStep, String coordRef)   // info on simulation length
    {
        int elemPart = this.getElem();
        int dep = this.getDepthLayer();
        //System.out.printf("RK4Step: Location = [%.6e,%.6e], Element = %d\n",this.getLocation()[0],this.getLocation()[1],elemPart);
        double[] advectStep = new double[2];
        this.nrList = neighbourCellsList(this.getLocation(), elemPart, 
            neighbours, uvnode, nodexy, trinodes, allelems, coordRef);
        //this.setNrListToNeighbourCells(neighbours,uvnode);
        
        // 2. Compute k_1 (spatial interpolation at start of step)
        //System.out.println("Start step");
        // Velocity from start of timestep
        double[] vel = velocityFromNearestList(this.getNrList(),tt,u,v,dep);
        // Velocity from end of this hour - will linearly interpolate to end of subTimeStep below and in stepAhead
        double[] velplus1 = velocityFromNearestList(this.getNrList(),tt+1,u,v,dep);
        //double[] velplus1 = getNextVel(tt,recordsPerFile,fnum,lastday,this.getNrList(),u,v,u1,v1,numLayers,dep);
        double[] k1 = new double[2];
        k1[0] = dt*(vel[0] + ((double)st/(double)stepsPerStep)*(velplus1[0]-vel[0]));
        k1[1] = dt*(vel[1] + ((double)st/(double)stepsPerStep)*(velplus1[1]-vel[1]));
        
        // 3. Compute k_2 (spatial interpolation at half step, temporal interp at half step)
        // Estimated half-step location using Euler
        //System.out.println("Half step (k1 -> k2)");
        // NOTE that here, for the purposes of identifying the elements containing the part-step locations,
        // the steps in degrees are calculated. These are separate of the actual half-step values in metres
        // which are retained and summed at the end of the method to give a transport distance in metres
        double[] k1Deg = new double[]{k1[0],k1[1]};
        if (this.coordRef.equalsIgnoreCase("WGS84"))
        {
            k1Deg = ParallelParticleMover.distanceMetresToDegrees2(k1Deg,this.getLocation());
        }
        double[] k2 = stepAhead(
                new double[]{this.getLocation()[0]+k1Deg[0]/2.0,this.getLocation()[1]+k1Deg[1]/2.0},
                elemPart,dep,1.0/2.0,
                neighbours,uvnode,nodexy,trinodes,allelems,u,v,
                tt,st,dt,stepsPerStep,coordRef);

        // 4. Compute k_3 (spatial interpolation at half step, temporal interp at half step)
        //System.out.println("Half step (k2 -> k3)");
        double[] k2Deg = new double[]{k2[0],k2[1]};
        if (this.coordRef.equalsIgnoreCase("WGS84"))
        {
            k2Deg = ParallelParticleMover.distanceMetresToDegrees2(k2Deg,this.getLocation());
        }
        double[] k3 = stepAhead(
                new double[]{this.getLocation()[0]+k2Deg[0]/2.0,this.getLocation()[1]+k2Deg[1]/2.0},
                elemPart,dep,1.0/2.0,
                neighbours,uvnode,nodexy,trinodes,allelems,u,v,
                tt,st,dt,stepsPerStep,coordRef);              
        
        // 5. Compute k_4 (spatial interpolation at end step)
        //System.out.println("End step (k3 -> k4)");
        double[] k3Deg = new double[]{k3[0],k3[1]};
        if (this.coordRef.equalsIgnoreCase("WGS84"))
        {
            k3Deg = ParallelParticleMover.distanceMetresToDegrees2(k3Deg,this.getLocation());
        }
        double[] k4 = stepAhead(
                new double[]{this.getLocation()[0]+k3Deg[0],this.getLocation()[1]+k3Deg[1]},
                elemPart,dep,1.0,
                neighbours,uvnode,nodexy,trinodes,allelems,u,v,
                tt,st,dt,stepsPerStep,coordRef);
        
        // 6. Add it all together
        if (k1[0] == 0 || k2[0] == 0 || k3[0] == 0 || k4[0] == 0)
        {
//            System.out.printf("RK4 attempt to step out of mesh: Location = [%.6e,%.6e], Element = %d\n",
//                    this.getLocation()[0],this.getLocation()[1],elemPart);
        } 
        else 
        {
            advectStep[0] = (k1[0] + 2*k2[0] + 2*k3[0] + k4[0])/6.0;
            advectStep[1] = (k1[1] + 2*k2[1] + 2*k3[1] + k4[1])/6.0;
        }
        
        // Check that things approximately tie up with corresponding Euler step distance
        // Generally they do, but sometimes they really don't (calculating Euler steps with
        // similar time step to RK4 is not advisable as sensitive to complex current features).
        // Further the approximation for printing below doesn't include the time interpolation.
//        System.out.printf("RK4 calc (%d): advectStep=[%.3e,%.3e] vel0=[%.3e,%.3e] dt=%.3e vel0*dt=[%.3e,%.3e]\n",
//                st,advectStep[0],advectStep[1],vel[0],vel[1],dt,vel[0]*dt,vel[1]*dt);
        return advectStep;
    }
    /**
     * Calculate the correction steps required for the RK4 algorithm
     * 
     * @param xy             Location of new velocity to be used (current location plus spatial step)
     * @param elemPart
     * @param dep
     * @param timeStepAhead   Time step ahead i.e. "2.0" if half-step ahead, "1.0" if full step ahead
     * @param neighbours
     * @param uvnode
     * @param nodexy
     * @param trinodes
     * @param allelems
     * @param u
     * @param v
     * @param tt
     * @param st
     * @param dt
     * @param stepsPerStep
     * @param coordRef
     * @return 
     */
    public static double[] stepAhead(double[] xy, int elemPart, int dep, double timeStepAhead,
            int[][] neighbours, float[][] uvnode, float[][] nodexy, int[][] trinodes, int[] allelems,
            float u[][][], float v[][][], 
            int tt, int st, double dt,
            int stepsPerStep, String coordRef)
    {   
        double[] xy_step = new double[2];
        
        // Generate a "neighbour cells list" for this location
        double[][] xNrList = neighbourCellsList(xy,elemPart,neighbours,uvnode,nodexy,trinodes,allelems,coordRef);
        
        // If predicted location of particle is outside mesh, return zero velocity
        if (xNrList[0][0] == 0)
        {
            return xy_step;
        }
        // compute velocities at start and end of entire step, at the new location
        double[] vel = velocityFromNearestList(xNrList,tt,u,v,dep);
        double[] velplus1 = velocityFromNearestList(xNrList,tt+1,u,v,dep);
        // Do the relevant temporal interpolation for this part of the step             
        xy_step[0] = dt*(vel[0] + ((double)(st+1.0/timeStepAhead)/(double)stepsPerStep)*(velplus1[0]-vel[0]));
        xy_step[1] = dt*(vel[1] + ((double)(st+1.0/timeStepAhead)/(double)stepsPerStep)*(velplus1[1]-vel[1]));
                
        return xy_step;
    }
    
    /**
     * Compute an Euler integration step for particle movement
     * 
     * @param u
     * @param v
     * @param neighbours
     * @param uvnode
     * @param nodexy
     * @param trinodes
     * @param allelems
     * @param tt
     * @param st
     * @param dt
     * @param stepsPerStep
     * @param coordRef
     * @return 
     */
    public double[] eulerStep(float u[][][], float v[][][], // velocities
        int[][] neighbours, float[][] uvnode, float[][] nodexy, 
        int[][] trinodes, int[] allelems,      // other mesh info
        int tt, int st, double dt,                                  // locate particle in space and time
        int stepsPerStep, String coordRef)
    {
        int elemPart = this.getElem();
        int dep = this.getDepthLayer();
        //System.out.printf("RK4Step: Location = [%.6e,%.6e], Element = %d\n",this.getLocation()[0],this.getLocation()[1],elemPart);
        double[] advectStep = new double[2];
        this.nrList = neighbourCellsList(this.getLocation(), elemPart, 
            neighbours, uvnode, nodexy, trinodes, allelems,coordRef);
        //this.setNrListToNeighbourCells(neighbours,uvnode);
        
//        // Generate a "nearest list" for this location
//        double[][] xNrList = neighbourCellsList(xy,elemPart,neighbours,uvnode,nodexy,trinodes,allelems);
        
        // If predicted location of particle is outside mesh, return zero velocity
        if (this.getNrList()[0][0] == 0)
        {
            return advectStep;
        }
        
        // compute velocities at start and end of entire step, at the new location
        double[] vel = velocityFromNearestList(this.getNrList(),tt,u,v,dep);
        double[] velplus1 = velocityFromNearestList(this.getNrList(),tt+1,u,v,dep);
        
        // Compute the advection step based on temporally interpolated velocities
        advectStep[0] = dt*(vel[0] + ((double)(st)/(double)stepsPerStep)*(velplus1[0]-vel[0]));
        advectStep[1] = dt*(vel[1] + ((double)(st)/(double)stepsPerStep)*(velplus1[1]-vel[1]));
        
        // Sense check calculated velocity and advection step (should be better when st close to 0)
//        System.out.printf("Euler calc (%d): advectStep=[%.3e,%.3e] vel0=[%.3e,%.3e] dt=%.3e vel0*dt=[%.3e,%.3e]\n",
//            st,advectStep[0],advectStep[1],vel[0],vel[1],dt,vel[0]*dt,vel[1]*dt);
        
        return advectStep;
    }   
    
        /**
     * Calculate velocity at a particle's location, given known
     * @param tt
     * @param u
     * @param v
     * @return 
     */
//    public double[] velPart(int tt, float u[][][], float v[][][])
//    {
//        double[] velocity = new double[2];
//        velocity = velocityFromNearestList(this.nrList,tt,u,v,1);
//        return velocity;
//    }
    
    /**
     * Method to compute velocity at a certain time and location, directly from an element of a velocity field.
     * 
     * This does the same thing as "velPart", except it generates a list of cells
     * to spatially interpolate from.
     * 
     * @param tt
     * @param xy
     * @param elemPart0
     * @param u
     * @param v
     * @param neighbours
     * @param uvnode
     * @param nodexy
     * @param trinodes
     * @param allelems
     * @return 
     */
//    public static double[] velocityInterpAtLoc(int tt, double xy[], int elemPart0, float u[][][], float v[][][],
//            int[][] neighbours, float[][] uvnode, float[][] nodexy, int[][] trinodes, int[] allelems, int depLayer, String coordRef)
//    {
//        double[] velocity = new double[2];
//        // elemPart0 is the starting search element. Set = 1 if outside range.
//        if (elemPart0 < 1 || elemPart0 > allelems.length)
//        {
//            elemPart0 = 1;
//            System.out.println("velocityInterpAtLoc picking default start element = 1");
//        }
//        double[][] cellList = neighbourCellsList(xy, elemPart0, neighbours, uvnode, nodexy, trinodes, allelems, coordRef);
//        velocity = velocityFromNearestList(cellList,tt,u,v,1);
//        return velocity;
//    }
    
        /**
     * Set the particle's nearestList (NrList) to be identical to the list of elements which 
     * neighbour the element that the particle is actually in
     * @param neighbours
     * @param uvnode 
     */
//    public void setNrListToNeighbourCells(int[][] neighbours, float[][] uvnode)
//    {      
//        // distance to elem
//        //int elem = nearestCentroid(this.xy[0],this.xy[1],uvnode);
//        int elem = this.elem;
//        this.nrList[0][0] = elem;
//        this.nrList[0][1] = distanceEuclid(this.xy[0],this.xy[1],uvnode[0][elem],uvnode[1][elem]);
//        // distance to neighbouring elems
//        this.nrList[1][0] = neighbours[0][elem];
//        this.nrList[1][1] = distanceEuclid(this.xy[0],this.xy[1],uvnode[0][neighbours[0][elem]],uvnode[1][neighbours[0][elem]]);
//        this.nrList[2][0] = neighbours[1][elem];
//        this.nrList[2][1] = distanceEuclid(this.xy[0],this.xy[1],uvnode[0][neighbours[1][elem]],uvnode[1][neighbours[1][elem]]);
//        this.nrList[3][0] = neighbours[2][elem];
//        this.nrList[3][1] = distanceEuclid(this.xy[0],this.xy[1],uvnode[0][neighbours[2][elem]],uvnode[1][neighbours[2][elem]]);   
//        this.nrList[4][0] = 0;
//        this.nrList[4][1] = 1000000;     
//    }
    
}