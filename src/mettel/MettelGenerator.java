/**
 * This file is part of MetTeL.
 *
 * MetTeL is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MetTeL is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MetTeL.  If not, see <http://www.gnu.org/licenses/>.
 */
package mettel;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler;

import org.antlr.Tool;
import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.tool.ErrorManager;
//import org.antlr.tool.ErrorManager;

//import org.antlr.Tool;

import mettel.generator.MettelANTLRGrammarGenerator;
import mettel.generator.MettelANTLRGrammarGeneratorProperties;
import mettel.generator.java.MettelJavaPackageStructure;
import mettel.language.MettelLexer;
import mettel.language.MettelParser;
import mettel.language.MettelSpecification;

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelGenerator {

    private static PrintWriter out = null;
    private static PrintWriter err = null;
    private static CharStream in = null;
    private static String outFileName = null;
    private static String errFileName = null;
    private static String outputPath = "output";
    private static FileReader prop = null;
    private static File tableau = null;
    private static boolean quiet = false;

    /**
     * @param args
     */
    public static void main(String[] args) throws IOException {

        System.out.println("-----------------------------------------------------------------");
        System.out.println("Hello there! I am MetTeL, an automated tableau prover generator.");
        System.out.println("I am designed and implemented by Dmitry Tishkovsky.");
        System.out.println("Copyright (c) 2009-2011 Dmitry Tishkovsky.");
        System.out.println("As a program, I have ABSOLUTELY NO WARRANTY.");//for details type `show w'.
        System.out.println("I am free software, and you are welcome to redistribute me");
        System.out.println("under certain conditions; use the option `--license' for details.");
        System.out.println("-----------------------------------------------------------------");
        try {
            final int SIZE = args.length;
            for (int i = 0; i < SIZE; i++) {
                if ("-q".equals(args[i]) || "--quiet".equals(args[i])) {

                    quiet = true;

                } else if ("-i".equals(args[i]) || "--input".equals(args[i])) {

                    if (i < SIZE - 1) {
                        in = new ANTLRFileStream(args[++i]);
                        System.out.println("Input file: " + args[i]);
                    } else {
                        System.out.println("I need a name of the input file.");
                        System.exit(-1);
                    }

                } else if ("-o".equals(args[i]) || "--output".equals(args[i])) {

                    if (i < SIZE - 1) {
                        outFileName = args[++i];
                        out = new PrintWriter(new FileWriter(outFileName), true);
                        System.out.println("Output file: " + outFileName);
                    } else {
                        System.out.println("I need a name of file for my output.");
                        System.exit(-1);
                    }

                } else if ("-e".equals(args[i]) || "--error".equals(args[i])) {

                    if (i < SIZE - 1) {
                        errFileName = args[++i];
                        err = new PrintWriter(new FileWriter(errFileName), true);
                        System.out.println("Using error file: " + errFileName);
                    } else {
                        System.out.println("I need a name of file for output about my errors.");
                        System.exit(-1);
                    }

                } else if ("-d".equals(args[i]) || "--output-directory".equals(args[i])) {

                    if (i < SIZE - 1) {
                        outputPath = args[++i];
                        System.out.println("Output path: " + outputPath);
                    } else {
                        System.out.println("I need a name of directory where I will put generated code.");
                        System.exit(-1);
                    }

                } else if ("-p".equals(args[i]) || "--properties".equals(args[i])) {

                    if (i < SIZE - 1) {
                        prop = new FileReader(args[++i]);
                        System.out.println("Properties file: " + args[i]);
                    } else {
                        System.out.println("I need a name of file where you have specified properties of the program I am going to generate.");
                        System.exit(-1);
                    }

                } else if ("--license".equals(args[i])) {

                    BufferedReader r = new BufferedReader(
                            new InputStreamReader(
                            MettelGenerator.class.getResourceAsStream("/etc/license")));
                    String s;
                    while ((s = r.readLine()) != null) {
                        System.out.println(s);
                    }
                    System.out.println();

                    r = new BufferedReader(
                            new InputStreamReader(
                            MettelGenerator.class.getResourceAsStream("/etc/license-additional-terms")));
                    while ((s = r.readLine()) != null) {
                        System.out.println(s);
                    }

                    System.exit(0);

                } else if ("-t".equals(args[i]) || "--tableau".equals(args[i])) {

                    if (i < SIZE - 1) {
                        tableau = new File(args[++i]);
                        System.out.println("Tableau file name: " + args[i]);
                    } else {
                        System.out.println("I need a name of file where you defined tableau rules.");
                        System.exit(-1);
                    }

                }
            }



            CommonTokenStream tokens = new CommonTokenStream();

            if (in == null) {
                in = new ANTLRInputStream(System.in);
            }

            MettelLexer lexer = new MettelLexer(in);

            tokens.setTokenSource(lexer);
            MettelParser parser = new MettelParser(tokens);

            if (out == null) {
                out = new PrintWriter(
                        new OutputStreamWriter(System.out), true);
            }
            if (err == null) {
                err = new PrintWriter(
                        new OutputStreamWriter(System.err), true);
            }

            report("I am reading the specification.");
            MettelSpecification spec = parser.specification();

            final int errorNumber = parser.getNumberOfSyntaxErrors();
            if (errorNumber > 0) {
                report("The specification contains " + errorNumber + " syntax errors.");
//        		err.println("I found "+errorNumber +"syntax errors in the specification.");
                System.exit(1);
            } else {
                report("The specification is OK.");
            }

            //StringBuilder buf = new StringBuilder();
            //spec.toBuffer(buf);
            //System.out.print(buf);
            MettelANTLRGrammarGeneratorProperties p = (prop == null) ? null : new MettelANTLRGrammarGeneratorProperties(prop);

            report("I am processing the specification.");
            MettelANTLRGrammarGenerator gen = new MettelANTLRGrammarGenerator(spec, p);
//        	StringBuilder buf = new StringBuilder();
            for (MettelJavaPackageStructure pStructure : gen.processSyntaxes()) {
                //System.out.println('#');
                pStructure.flush(outputPath);
            }
            report("Java code of the prover is generated.");



            CodeSource src = MettelGenerator.class.getProtectionDomain().getCodeSource();
            String zipfile = "metsrc.zip";
            InputStream mySource = MettelGenerator.class.getClassLoader().getResourceAsStream(zipfile);
            ZipInputStream zipinputstream = new ZipInputStream(mySource);


            ZipEntry entry = null;
            String destinationname = outputPath;
            byte[] buf = new byte[1024];
            int BUFFER = 2048;
            new File(destinationname).mkdirs();

            while ((entry = zipinputstream.getNextEntry()) != null) {


                String currentEntry = entry.getName();
                File destFile = new File(destinationname, currentEntry);
                //destFile = new File(newPath, destFile.getName());
                File destinationParent = destFile.getParentFile();

                // create the parent directory structure if needed
                destinationParent.mkdirs();

               //System.out.println( entry.toString());
                if (!entry.isDirectory())
                {

                int n;
                FileOutputStream fileoutputstream = new FileOutputStream(destFile);             

                while ((n = zipinputstream.read(buf, 0, 1024)) > -1)
                    fileoutputstream.write(buf, 0, n);

                fileoutputstream.close(); 
                zipinputstream.closeEntry();
                }

            }


            /*        	out.print(buf);
            out.flush();
            out.close();
             */
            /*        	String[] antlrArgs = {"-o", "/var/tmp/", "-print", outFileName};
            if(outFileName == null){
            System.err.print("ANTLR file name required");
            System.exit(0);
            }
            
            Tool antlr = new Tool(antlrArgs);
            antlr.process();
            if(ErrorManager.getNumErrors() > 0){
            System.exit(1);
            }
             */
            if (tableau != null) {
                if (build(spec.path())) {
                    System.exit(0);
                }
                System.exit(1);
            }

            System.exit(0);
        } catch (Exception e) {

            out.println("Sorry! I detected an exceptional situation and terminate now.");
            out.println("If you can help me to avoid this situation in future, please look at my error output.");
            out.flush();
            err.println("==Exception==========================");
            e.printStackTrace(err);
            err.println("=====================================");
            err.flush();
            System.exit(-1);

        }

    }

    private static void report(String s) {
        if (!quiet) {
            out.println(s);
            out.flush();
        }
    }

    private static boolean build(String path) throws IOException {

        File src = new File(outputPath + File.separatorChar + path);
        File grammar = findGrammarFile(src);

        String[] antlrArguments = {
            grammar.getAbsolutePath()
        };

        report("I have asked ANTLR to generate a parser for the prover.");
        Tool antlr = new Tool(antlrArguments);
        antlr.process();
        final int errorNumber = ErrorManager.getNumErrors();
        if (errorNumber > 0) {
            report("ANTLR reported " + errorNumber + " errors.");
            return false;
        }
        report("The Java code for the parser is generated.");

        report("I am trying to compile the generated code.");
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        if (compiler == null) {
            throw new RuntimeException("No compiler available");
        }

        File dir = createTempDir(path);

        List<String> arguments = new ArrayList<String>();
        //arguments.add("-verbose");
        arguments.add("-d");
        arguments.add(dir.getAbsolutePath());
        arguments.addAll(listFileNames(src));
        arguments.addAll(listFileNames(new File(outputPath + File.separatorChar + "mettel")));

        OutputStream out0 = System.out;
        if (outFileName != null) {
            out0 = new FileOutputStream(outFileName, true);
        }

        OutputStream err0 = System.err;
        if (errFileName != null) {
            err0 = new FileOutputStream(errFileName, true);
        }

        if (compiler.run(null, out0, err0, arguments.toArray(new String[arguments.size()])) != 0) {
            out0.flush();
            err0.flush();
            report("Compilation failed.");
            return false;
        }
        out0.flush();
        err0.flush();

        report("I am trying to make an executable jar-file with the prover.");

        File mainClass = findMainClass(src);
        String name = mainClass.getName();
        name = name.substring(0, name.lastIndexOf('.'));

        Manifest manifest = new Manifest();
        Attributes attributes = manifest.getMainAttributes();
        attributes.put(Attributes.Name.MANIFEST_VERSION, "1.0");
        attributes.put(Attributes.Name.MAIN_CLASS, path + '.' + name);
        //attributes.put(Attributes.Name.CLASS_PATH, "mettel2.jar antlr3-runtime.jar");
        attributes.put(Attributes.Name.CLASS_PATH, "antlr3-runtime.jar");

        JarOutputStream jar = new JarOutputStream(new FileOutputStream(path + ".jar"), manifest);
        addToJar(dir.getPath(), dir, jar);

        //ClassLoader cl = new ClassLoader();

        addTableauToJar(path, tableau, jar);
        jar.close();

        report("The jar is made.");

        deleteFiles(dir);

        return true;
    }

    private static void addTableauToJar(String path, File source, JarOutputStream target) throws IOException {
        JarEntry entry = new JarEntry(path + "/tableau/calculus");
        entry.setTime(source.lastModified());
        target.putNextEntry(entry);
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(source));

        byte[] buffer = new byte[1024];
        while (true) {
            int count = in.read(buffer);
            if (count == -1) {
                break;
            }
            target.write(buffer, 0, count);
        }
        target.closeEntry();
    }

    private static void addToJar(String prefix, File source, JarOutputStream target) throws IOException {
        BufferedInputStream in = null;
        try {
            if (source.isDirectory()) {
                String name = source.getPath().replace(prefix, "").replace(File.separator, "/");
                if (!name.isEmpty()) {
                    if (!name.endsWith("/")) {
                        name += "/";
                    }
                    if (name.startsWith("/")) {
                        name = name.substring(1);
                    }
                    JarEntry entry = new JarEntry(name);
                    entry.setTime(source.lastModified());
                    target.putNextEntry(entry);
                    target.closeEntry();
                }
                for (File nestedFile : source.listFiles()) {
                    addToJar(prefix, nestedFile, target);
                }
                return;
            }

            String name = source.getPath().replace(prefix, "").replace(File.separator, "/");
            if (name.startsWith("/")) {
                name = name.substring(1);
            }
            JarEntry entry = new JarEntry(name);
            entry.setTime(source.lastModified());
            target.putNextEntry(entry);
            in = new BufferedInputStream(new FileInputStream(source));

            byte[] buffer = new byte[1024];
            while (true) {
                int count = in.read(buffer);
                if (count == -1) {
                    break;
                }
                target.write(buffer, 0, count);
            }
            target.closeEntry();
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    public static File createTempDir(String prefix) {

        final int TEMP_DIR_ATTEMPTS = 10000;

        final File systemTempDir = new File(System.getProperty("java.io.tmpdir"));
        final String random = System.currentTimeMillis() + "_";

        for (int counter = 0; counter < TEMP_DIR_ATTEMPTS; counter++) {

            final File dir = new File(systemTempDir, prefix + random + counter);
            if (dir.mkdir()) {
                return dir;
            }

        }
        throw new IllegalStateException("Failed to create directory "
                + prefix + random + " within " + TEMP_DIR_ATTEMPTS);

    }

    private static List<File> listFiles(List<File> files, File dir) {

        if (files == null) {
            files = new ArrayList<File>();
        }

        if (!dir.isDirectory()) {
            String name = dir.getName();
            if (name.endsWith(".java")) {
                files.add(dir);
            }
            return files;
        }

        if (!dir.getName().equals("test")) {
            for (File file : dir.listFiles()) {
                listFiles(files, file);
            }
        }
        return files;

    }

    private static void deleteFiles(File dir) {

        if (!dir.isDirectory()) {
            dir.delete();
            return;
        }

        for (File file : dir.listFiles()) {
            deleteFiles(file);
        }
        dir.delete();
    }

    private static File findGrammarFile(File dir) {

        if (!dir.isDirectory()) {
            String name = dir.getName();
            if (name.endsWith(".g")) {
                return dir;
            }
            return null;
        }

        for (File file : dir.listFiles()) {
            File result = findGrammarFile(file);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    private static File findMainClass(File dir) {

        if (!dir.isDirectory()) {
            String name = dir.getName();
            if (name.endsWith("TableauProver.java")) {
                return dir;
            }
            return null;
        }

        for (File file : dir.listFiles()) {
            File result = findMainClass(file);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    private static List<String> listFileNames(File dir) {
        List<File> fileList = listFiles(null, dir);
        List<String> result = new ArrayList<String>();
        for (File f : fileList) {
            result.add(f.getAbsolutePath());
        }
        return result;
    }
}
