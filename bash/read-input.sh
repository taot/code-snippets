############################################################
# 1. Read lines from a file.
# 2. Cut the lines to several parts.
# 3. Invoke another function.
############################################################
while read LINE; do
  APPNAME=$(echo $LINE | cut -d ' ' -f 1)
  LOCATION=$(echo $LINE | cut -d ' ' -f 2)
  CONFNAME=$(echo $LINE | cut -d ' ' -f 3)
  if [ -z $CONFNAME ] ; then
    CONFNAME=$APPNAME
  fi
  copy_deploy_to_dev $APPNAME $LOCATION $CONFNAME
done < $BASE_DIR/applist
