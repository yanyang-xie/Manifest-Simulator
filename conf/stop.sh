#!/usr/bin/env bash

p_id=`ps aux|grep com.thistech.simulator.ManifestApplication|grep -v grep|awk '{print $2}'`
if [ "$p_id" != "" ]
then
    kill -9 $p_id
fi

echo "killed ManifestApplication"