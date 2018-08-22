#!/bin/sh
#
# you'll need to change this for your own setup
#
export SAMHOME=/home/jconfig/samizdat

	# blackdown
export JAVA_HOME=/usr/local/jdk118
	# blackdown
#export JAVA_HOME=/usr/local/jre1.2.2
	# ibm
#export JAVA_HOME=/usr/jre118


export DISPLAY=:0
export PATH=$SAMHOME/common/:$JAVA_HOME/bin:$PATH
export LD_LIBRARY_PATH=$SAMHOME/common:$LD_LIBRARY_PATH
export CLASSPATH=.:$SAMHOME/common/JConfig.zip:$SAMHOME/common/imeister.zip:$JAVA_HOME/lib/classes.zip:$JAVA_HOME/lib/rt.jar

# add your filters to the classpath here
# for instance, if you write a plugin called "vztv9832.zip",
# you would add it to the classpath as follows:
# export CLASSPATH=$CLASSPATH:$SAMHOME/imeister/plugins/vztv9832.zip
# you would also add the full class name of the plugin to the
# file "plugins.txt", which is inside the "data" folder
# see the file tutorial.html for more information

    # jre118
#$JAVA_HOME/bin/jre -cp $CLASSPATH com.tolstoy.imagemeister.Starter

    # jdk118 and jre1.2.2
$JAVA_HOME/bin/java -classpath $CLASSPATH com.tolstoy.imagemeister.Starter
