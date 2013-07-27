package com.taot.guice;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * TODO this is not finished yet.
 * @author taot
 *
 */
public abstract class AutoScanModule {

    protected void bindPackage(String srcPkg, String dstPkg) throws IOException, ClassNotFoundException {

        long t = System.currentTimeMillis();
        
        final String resource = "com/google/inject";
        
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> urls = classLoader.getResources(resource);
        while (urls.hasMoreElements()) {
            final URL url = urls.nextElement();
            System.out.println(url.getProtocol() + " " + url.toExternalForm());
            if ("jar".equals(url.getProtocol())) {
                JarURLConnection jarUrlConnection = (JarURLConnection) url.openConnection();
                JarFile jarFile = jarUrlConnection.getJarFile();
                Enumeration<JarEntry> entries = jarFile.entries();
                while (entries.hasMoreElements()) {
                    JarEntry ent = entries.nextElement();
                    if (ent.getName().startsWith(resource)) {
                        String remaining = ent.getName().substring( resource.length() + 1);
                        
//                        System.out.println(remaining);
                        if (!remaining.contains("/") && !remaining.contains("$") && remaining.length() > 0
                                       && remaining.endsWith(".class")) {
                            String shortClassName = remaining.substring(0, remaining.length() - 6);
                            String className = resource.replace('/', '.') + "." + shortClassName;
                            System.out.println(className);
                            Class<?> clazz = Class.forName(className); //, true, classLoader);
                            System.out.println(clazz);
                        }
                    }
                }
            }
        }
        System.out.println(System.currentTimeMillis() - t);
    }
}
