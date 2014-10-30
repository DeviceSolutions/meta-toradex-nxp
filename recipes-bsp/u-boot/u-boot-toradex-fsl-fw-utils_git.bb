DESCRIPTION = "U-boot bootloader fw_printenv/setenv utils"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM_colibri-vf = "file://Licenses/README;md5=c7383a594871c03da76b3707929d2919"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=025bf9f768cbcb1a165dbe1a110babfb"
SECTION = "bootloader"
PROVIDES = "u-boot-fw-utils"
DEPENDS = "mtd-utils"

COMPATIBLE_MACHINE = "(colibri-vf|colibri-imx6|apalis-imx6)"
DEFAULT_PREFERENCE_colibri-vf = "1"
DEFAULT_PREFERENCE_apalis-imx6 = "1"
DEFAULT_PREFERENCE_colibri-imx6 = "1"

FILESPATHPKG =. "git:"
S="${WORKDIR}/git"
SRCREV_colibri-vf = "67c3a4aee6c0af124ce779aed1be70c7a92d0916"
SRCREV_mx6 = "d4c752ac0eca88873e73a5a3bee6573f6d0ec92a"
SRCBRANCH_colibri-vf = "2014.10-toradex"
SRCBRANCH_mx6 = "2014.04-toradex"
SRC_URI = "git://git.toradex.com/u-boot-toradex.git;protocol=git;branch=${SRCBRANCH} \
           file://fw_env.config \
"

PV = "${PR}+gitr${SRCREV}"
PR = "r0"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = 'CROSS_COMPILE=${TARGET_PREFIX} CC="${TARGET_PREFIX}gcc ${TOOLCHAIN_OPTIONS}"'

inherit uboot-config

do_compile () {
    oe_runmake ${UBOOT_MACHINE}
    oe_runmake env
}

do_install_colibri-vf () {
    install -d ${D}${base_sbindir} ${D}${sysconfdir}
    install -m 755 ${S}/tools/env/fw_printenv ${D}${base_sbindir}/fw_printenv
    ln -s fw_printenv ${D}${base_sbindir}/fw_setenv
    install -m 644 ${WORKDIR}/fw_env.config ${D}${sysconfdir}/
}

do_install () {
    install -d ${D}${base_sbindir} ${D}${sysconfdir}
    install -m 755 ${S}/tools/env/fw_printenv_unstripped ${D}${base_sbindir}/fw_printenv
    ln -s fw_printenv ${D}${base_sbindir}/fw_setenv
    install -m 644 ${WORKDIR}/fw_env.config ${D}${sysconfdir}/
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
