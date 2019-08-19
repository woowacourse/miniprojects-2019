# Download docer-compose.yml, nginx configuration and static resources

sudo snap install docker

if [ $1 == "production" ]; then
    git clone -b EDD https://github.com/woowacourse/miniprojects-2019
elif [ $1 == "dev" ]; then
    git clone -b $3 https://github.com/$2/miniprojects-2019
else
    echo 'Argument [production, develop] is required'
fi

cd miniprojects-2019

sudo docker-compose up -d
