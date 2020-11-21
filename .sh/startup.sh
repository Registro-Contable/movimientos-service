#!/bin/sh
# startup.sh

JAR=$1
CLOUD_CONFIG_SERVER=$(awk 'BEGIN {print ENVIRON["spring.cloud.config.uri"]}')

echo "Servidor de configuracion:" $CLOUD_CONFIG_SERVER

echo "Consultado estado de servidor de configuracion..."
status_code=$(curl --write-out '%{http_code}' --silent --output output_data.temp $CLOUD_CONFIG_SERVER/actuator/health)

if [[ "$status_code" -ne 200 ]] ; then
        echo "[EXIT] Error al conectar con el servidor de configuracion -- status_code=" $status_code
        exit 1
fi

response=$(cat output_data.temp)
echo "Respuesta del servidor correcta [status_code=200][content="$response"]"

if [[ "$response" != "{\"status\":\"UP\"}" ]] ; then
        echo "[EXIT] El servidor no ha respondido correctamente"
        exit 1
fi

java -jar $JAR