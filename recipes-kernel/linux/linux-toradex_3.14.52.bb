require recipes-kernel/linux/linux-imx.inc
require recipes-kernel/linux/linux-dtb.inc

SUMMARY = "Linux kernel for Toradex Freescale i.MX based modules"

SRC_URI = "git://git.toradex.com/linux-toradex.git;protocol=git;branch=${SRCBRANCH} \
           file://defconfig"

# Load USB functions configurable through configfs (CONFIG_USB_CONFIGFS)
KERNEL_MODULE_AUTOLOAD += "${@bb.utils.contains('COMBINED_FEATURES', 'usbgadget', ' libcomposite', '',d)}"

LOCALVERSION = "-v2.6b2"
SRCBRANCH = "toradex_imx_3.14.52_1.1.0_ga"
SRCREV = "7c83cef87116c0b2aaaf867f406831643306d3d1"

DEPENDS += "lzop-native bc-native"
COMPATIBLE_MACHINE = "(mx6|mx7)"
