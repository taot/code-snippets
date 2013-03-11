############################################################
# 1. Find the files having specific strings.
# 2. Prepend a line to the beginning of the file.
# 3. Replace strings
############################################################
grep -l -e "mongodb\.replicaset" -e "include.*mongo\.prod.*\.conf" * | while read FILE; do
  sed -i \
    -e "1 i include classpath(\"mongo/mongo.$MONGO_ENV.conf\")" \
    -e '/mongodb\.replicaset/,/\}/d' \
    -e '\|include.*mongo\.prod.*\.conf|d' \
    $FILE
done
