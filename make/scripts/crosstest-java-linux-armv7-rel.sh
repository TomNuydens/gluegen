export HOST_UID=sven
export HOST_IP=192.168.0.52
export HOST_RSYNC_ROOT=PROJECTS/JOGL

export TARGET_UID=jogamp
export TARGET_IP=beagle01
export TARGET_ROOT=projects-cross

export ANT_PATH=/usr/share/ant
export BUILD_DIR=../build-linux-armv7

#TSTCLASS=com.jogamp.gluegen.test.junit.generation.Test1p2ProcAddressEmitter
TSTCLASS=com.jogamp.common.GlueGenVersion

LOGFILE=`basename $0 .sh`.log

RSYNC_EXCLUDES="--exclude 'build-x86*/' --exclude 'build-linux-x*/' --exclude 'build-android*/' --exclude 'build-win*/' --exclude 'build-mac*/' \
                --exclude 'classes/' --exclude 'src/' --exclude '.git/' \
                --delete-excluded"

ssh $TARGET_UID@$TARGET_IP "\
rsync -aAv --delete --delete-after $RSYNC_EXCLUDES $HOST_UID@$HOST_IP::$HOST_RSYNC_ROOT/gluegen $TARGET_ROOT ; \
cd $TARGET_ROOT/gluegen/make ;
LD_LIBRARY_PATH=$BUILD_DIR/obj:$BUILD_DIR/test/build/natives \
java \
  -Djava.library.path=$BUILD_DIR/obj:$BUILD_DIR/test/build/natives \
  -Djava.class.path=lib/junit.jar:$ANT_PATH/lib/ant.jar:$ANT_PATH/lib/ant-junit.jar:$BUILD_DIR/gluegen.jar:$BUILD_DIR/test/build/gluegen-test.jar \
  -Djogamp.debug.JNILibLoader=true \
  -Djogamp.debug.NativeLibrary=true \
  -Djogamp.debug.NativeLibrary.Lookup=true \
  -Djogamp.debug.ProcAddressHelper=true \
  $TSTCLASS \
 2>&1 | tee $LOGFILE \
"

scp $TARGET_UID@$TARGET_IP:$TARGET_ROOT/gluegen/make/$LOGFILE .
