package S4.tableau;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FilenameFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.LinkedHashSet;
import java.util.Set;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import S4.language.*;
import S4.S4TableauProver;

import mettel.core.tableau.MettelGeneralTableauRule;

import au.com.bytecode.opencsv.CSVWriter;


import static mettel.util.MettelStrings.FILE_SEPARATOR;

public class S4Benchmark{

    private static String outputPath = "benchmark";
    private static BufferedWriter out = null;
    private static File inDir = null;
    private static File outDir = null;
    private static CharStream in = null;
    private static long timeOutNanoSeconds = 1 * 1000000000;
    private static CSVWriter csvOut;

    final private static CommonTokenStream tokens = new CommonTokenStream();
    final private static S4Parser parser = new S4Parser(tokens);

    final private static String[] header = {"File name", "Satisfiable", "Model's/Contradiction's file name", "Execution-time"};

    //TODO make class in some utility probably
    private static class MtlFilter implements FilenameFilter{
        public boolean accept(File dir, String name){
            return name.toLowerCase().endsWith(".mtl");
        }
    }

    public static void main(String[] args){
        try{
            parseCommandLineArguments(args);
            
            //TODO exception probably
            if (inDir == null){
                System.out.println("NO INPUT!!!");
                System.exit(-1);
            }
            if (!inDir.isDirectory()){
                System.out.println("provided input is not a directory");
                System.exit(-1);
            }

            csvOut.writeNext(header);
            csvOut.flush();

            LinkedHashSet<MettelGeneralTableauRule> calculus = new LinkedHashSet<MettelGeneralTableauRule>();
            parseCalculus(calculus, null/*?tableauFile*/);


            for (String problemFile : inDir.list(new MtlFilter())){

                in = new ANTLRFileStream(inDir + FILE_SEPARATOR + problemFile);

                tokens.setTokenSource(new S4Lexer(in));

                ArrayList<S4Formula> list = new ArrayList<S4Formula>();
                parser.formulas(list);

                S4ProverThread pt = new S4ProverThread(calculus, list);
                pt.start();
                long nanos = -1;
                while(true){
                    if (!pt.isAlive()){
                        System.out.println("time from main " + nanos);
                        System.out.println("time from thread" + pt.time());
                        System.out.println("FINISHED " + pt.result());
                        break;
                    }
                    nanos = ManagementFactory.getThreadMXBean().getThreadCpuTime(pt.getId());
                    if (nanos >= timeOutNanoSeconds){
                        pt.stop();
                        System.out.println("BREAKING" + nanos);
                        break;
                    }

                    Thread.sleep(10);
                }

                String[] problemInfo = new String[header.length];

                problemInfo[0] = problemFile;
                if (pt.finished()){

                    String resultFile = problemFile.replace(".mtl", ".out");
                    out = new BufferedWriter(new FileWriter(outDir + FILE_SEPARATOR + resultFile));
                    out.write((pt.model() == null) ? pt.contradiction().toString() : pt.model().toString());
                    out.close();

                    problemInfo[1] = String.valueOf(pt.result());
                    problemInfo[2] = resultFile;
                    problemInfo[3] = String.valueOf(pt.time());
                }
                else{
                    // maybe delete .out file? because maybe user once run it with -t 100000 and later with -t 1 and one will be wrong
                    // or maybe delete all .out files before running whole program?
                    problemInfo[1] = "N/A";
                    problemInfo[2] = "N/A";
                    problemInfo[3] = "TIME OUT";
                }
                csvOut.writeNext(problemInfo);
                csvOut.flush();

            }
        }
        catch(Exception e){
            System.out.println("Sorry! I detected an exceptional situation and terminate now.");
            System.out.println("If you can help me to avoid this situation in future, please look at my error output.");
            System.err.println("==Exception==========================");
            e.printStackTrace(System.err);
            System.err.println("=====================================");
            System.exit(-1);            
        }

    }


    private static void parseCommandLineArguments(String[] args) throws IOException{
        final int SIZE = args.length;
        for(int i = 0; i < SIZE; i++){
            // not needed -d?
            if("-d".equals(args[i])||"--output-directory".equals(args[i])){
                if(i < SIZE-1){
                    //outputPath = args[++i];
                    System.out.println("Output path: " + outputPath);
                }else{
                    System.out.println("I need a name of directory where I will put generated random expressions.");
                    System.exit(-1);
                }
            }else if("-i".equals(args[i])||"--input-directory".equals(args[i])){
                if(i < SIZE-1){
                    inDir = new File(args[++i]);
                    outDir = inDir;
                    csvOut = new CSVWriter(new FileWriter(inDir + FILE_SEPARATOR + "S4.csv"));
                    System.out.println("Input directory: " + args[i]);
                }else{
                    System.out.println("I need a name of file where you have specified properties of the random expression generator.");
                    System.exit(-1);
                }
            }else if("-t".equals(args[i])||"--timeout".equals(args[i])){
                if(i < SIZE-1){
                    timeOutNanoSeconds = Long.parseLong(args[++i]);
                    System.out.println("Starting file index is: " + timeOutNanoSeconds);
                }else{
                    System.out.println("I need a number which specifies time out.");
                    System.exit(-1);
                }
            }
        }
    }

    public static void parseCalculus(Set<MettelGeneralTableauRule> calculus, String path)
        throws IOException, RecognitionException{
            
            CharStream tin = new ANTLRFileStream("../test/examples/S4/tableau");
            /*
            CharStream tin = (path == null)?
                new ANTLRInputStream(S4TableauProver.class.getResourceAsStream("/S4/tableau/calculus")):
                new ANTLRFileStream(path);
            */
            tokens.setTokenSource(new S4Lexer(tin));
            parser.tableauCalculus(calculus);
        }

}
// just in time compilation
// garbage collector