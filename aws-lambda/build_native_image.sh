#!/bin/bash

gu install native-image
mvn clean package -Pnative
