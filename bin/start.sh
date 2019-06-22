#!/bin/bash
case "`uname`" in
    Linux)
                bin_abs_path=$(readlink -f $(dirname $0))
                ;;
        *)
                bin_abs_path=`cd $(dirname $0); pwd`
                ;;
esac
project_abs_path=`cd ${bin_abs_path}/.. ;pwd`

config_abs_path=$project_abs_path"/config"

libs_abs_path=$project_abs_path"/libs"

logs_abs_path=$project_abs_path"/logs"

JARS="" #存放所有jar的路径
jar=".jar" #匹配jar

for file in `ls $libs_abs_path`
do
 if [[ $file == *$jar ]]
 then
    JARS=$JARS$libs_abs_path$file" "
 else
    echo "libs文件夹里面有非jar文件"
fi
done
echo $JARS


JAVA="" #存放java的路径
if [ -z "$JAVA" ] ; then
  JAVA=$(which java)
fi
echo $JAVA

#创建日志文件
if [ ! -f $logs_abs_path/app.log ] ; then
        touch $logs_abs_path/app.log
fi


#执行jar包
JAVA -cp $libs_abs_path APP 1>$logs_abs_path/app.log 2>&1