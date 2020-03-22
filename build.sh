#!/usr/bin/env bash

JAVA8_HOME="/usr/local/jdk1.8.0_65"
if [ -d "${JAVA8_HOME}" ]; then
    export JAVA_HOME=${JAVA8_HOME}
    export PATH=$JAVA_HOME/bin:$PATH
fi

output="$(pwd)/output"
mkdir -p $output

echo "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"
echo "[INFO]开始Maven打包......"
echo "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"


mvn clean package -Dmaven.test.skip=true -U

if [ $? -ne 0 ]; then
    echo ""
    echo "***********************************************************"
    echo "[INFO]Maven打包失败!!!"
    echo "***********************************************************"
    exit 1
fi

rm -rf ${output}/*
cp seckill-api/target/seckill-api.jar ${output}/seckill-api.jar.build

if [ $? -ne 0 ]; then
    echo ""
    echo "***********************************************************"
    echo "[INFO]移动打包文件失败!!!"
    echo "***********************************************************"
    exit 1
fi
echo ""
echo "***********************************************************"
echo "[INFO]Maven打包成功"
echo "***********************************************************"
