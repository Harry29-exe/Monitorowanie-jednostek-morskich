if [[ $UID != 0 ]]; then
    echo "Please run this script with sudo:"
    echo "sudo $0 $*"
    exit 1
fi

sudo docker container kill mjm-db;
sudo docker container rm mjm-db;
sudo docker image rm mjm-db;
sudo docker build ./postgres --tag mjm-db;
sudo docker run -p 5432:5432 -d --name mjm-db mjm-db;

sudo docker container kill mjm;
sudo docker container rm mjm;
sudo docker image rm mjm;
sudo docker build ./ --tag mjm;
#printf "\n\n ----------------------"
#printf "secret = %s" "$1"
#printf " ----------------------\n\n"
sudo docker run -p 8080:8080 -e secret=$1 --net=host --name mjm mjm
