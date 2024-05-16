FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}-${PV}:"
require recipes-kernel/linux/linux-imx.inc

SUMMARY = "Linux kernel for Toradex Freescale i.MX based modules"
SUMMARY:preempt-rt = "Real-Time Linux kernel for Toradex Freescale i.MX based modules"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

SRC_URI = " \
    git://git.toradex.com/linux-toradex.git;protocol=https;branch=${SRCBRANCH};name=machine \
"
SRC_URI:append = " \
	file://0001-Add-ATDM-carrier-board-support.patch \
	file://0002-Add-touch-driver-from-latest-kernel-source-6.x.patch \
	file://0003-Done-final-adjustment-for-LCD-and-touch-in-device-tr.patch \
	file://0004-Enable-second-ethernet-port.patch \
	file://0001-Gpio-Develpment.patch \
	file://0001-GPIO-Development-continue.patch \
	file://0001-Audio-codec-support-added.patch \
	file://0001-max31-chip-configure-in-daisy-chain-mode-for-2-slave.patch \
	file://0001-Fix-max3191-read-issue-also-add-second-ethernet.patch \
	file://0001-Add-second-ethernet-as-fix.patch \
	file://atdm_driver.cfg"

# Load USB functions configurable through configfs (CONFIG_USB_CONFIGFS)
KERNEL_MODULE_AUTOLOAD += "${@bb.utils.contains('COMBINED_FEATURES', 'usbgadget', ' libcomposite', '',d)}"

inherit toradex-kernel-deploy-config toradex-kernel-localversion
LINUX_VERSION = "5.15.148"
# skip, as with use-head-next LINUX_VERSION might be set wrongly
KERNEL_VERSION_SANITY_SKIP = "1"

SRCBRANCH = "toradex_5.15-2.2.x-imx"
SRCREV_machine = "23a8e831749daa8b77e349d827715dd1d7f8e9a5"
SRCREV_machine:use-head-next = "${AUTOREV}"

DEPENDS += "bc-native alsa-utils"
COMPATIBLE_MACHINE = "mx8-nxp-bsp"

KBUILD_DEFCONFIG:mx8-nxp-bsp ?= "toradex_defconfig"

export DTC_FLAGS = "-@"

###############################################################################
# Apply the RT patch and change the configuration to use PREMPT_RT when the
# preempt-rt override is set.
###############################################################################

# patches get moved into the 'older' directory when superseeded, so provide
# both possible storage locations.
MIRRORS:append:preempt-rt = "${KERNELORG_MIRROR}/linux/kernel/projects/rt/5.15/older/ ${KERNELORG_MIRROR}/linux/kernel/projects/rt/5.15/"
SRC_URI:append:preempt-rt = " \
    file://0001-Revert-Revert-ARM-9113-1-uaccess-remove-set_fs-imple.patch \
    file://0002-arch-arm-Kconfig-prepare-for-rt-patch.patch \
    ${KERNELORG_MIRROR}/linux/kernel/projects/rt/5.15/older/patch-5.15.148-rt74.patch.xz;name=rt-patch \
    file://0004-Revert-arch-arm-Kconfig-prepare-for-rt-patch.patch \
    file://0005-Revert-Revert-Revert-ARM-9113-1-uaccess-remove-set_f.patch \
    file://preempt-rt.scc \
    file://preempt-rt-less-latency.scc \
"

SRC_URI[rt-patch.sha256sum] = "b3494bd8c156550b3bfe27e544928e7ee3f0822d182ed77de54ebef867c67c6d"
