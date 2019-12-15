TARGETS = console-setup mountkernfs.sh resolvconf ufw hostname.sh apparmor screen-cleanup plymouth-log udev keyboard-setup cryptdisks cryptdisks-early hwclock.sh mountdevsubfs.sh checkroot.sh lvm2 checkfs.sh open-iscsi networking iscsid urandom mountall.sh checkroot-bootclean.sh mountnfs-bootclean.sh mountnfs.sh bootmisc.sh mountall-bootclean.sh procps kmod
INTERACTIVE = console-setup udev keyboard-setup cryptdisks cryptdisks-early checkroot.sh checkfs.sh
udev: mountkernfs.sh
keyboard-setup: mountkernfs.sh udev
cryptdisks: checkroot.sh cryptdisks-early udev lvm2
cryptdisks-early: checkroot.sh udev
hwclock.sh: mountdevsubfs.sh
mountdevsubfs.sh: mountkernfs.sh udev
checkroot.sh: hwclock.sh keyboard-setup mountdevsubfs.sh hostname.sh
lvm2: cryptdisks-early mountdevsubfs.sh udev
checkfs.sh: cryptdisks lvm2 checkroot.sh
open-iscsi: networking iscsid
networking: mountkernfs.sh urandom resolvconf procps
iscsid: networking
urandom: hwclock.sh
mountall.sh: checkfs.sh checkroot-bootclean.sh lvm2
checkroot-bootclean.sh: checkroot.sh
mountnfs-bootclean.sh: mountnfs.sh
mountnfs.sh: networking
bootmisc.sh: mountnfs-bootclean.sh mountall-bootclean.sh checkroot-bootclean.sh udev
mountall-bootclean.sh: mountall.sh
procps: mountkernfs.sh udev
kmod: checkroot.sh
