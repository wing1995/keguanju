# !/bash/bin

##In this script we will load the JNAWeb
##writen by wangyang 2015.10.02
##update by wuying 2016.3.17

cd JAnalyServer/src

## compile
javac -classpath ../../JarPacket/jna-4.2.1.jar:../../JarPacket/JBATNetSDK.jar:../../JarPacket/dom4j-1.6.1.jar:../../JarPacket/postgresql-9.4.1208.jre7.jar:. -d ../bin com/sibat/AnalyServer.java

## run
cd ../bin
java -classpath ../../JarPacket/jna-4.2.1.jar:../../JarPacket/JBATNetSDK.jar:../../JarPacket/dom4j-1.6.1.jar:../../JarPacket/postgresql-9.4.1208.jre7.jar:. com.sibat.AnalyServer

