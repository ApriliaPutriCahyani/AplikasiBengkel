/*
 Navicat Premium Data Transfer

 Source Server         : sase
 Source Server Type    : MySQL
 Source Server Version : 100137
 Source Host           : localhost:3306
 Source Schema         : university_aplikasi_bengkel

 Target Server Type    : MySQL
 Target Server Version : 100137
 File Encoding         : 65001

 Date: 16/05/2020 15:00:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for m_pelanggan
-- ----------------------------
DROP TABLE IF EXISTS `m_pelanggan`;
CREATE TABLE `m_pelanggan`  (
  `kode_pel` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `nama` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`kode_pel`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of m_pelanggan
-- ----------------------------
INSERT INTO `m_pelanggan` VALUES ('P01', 'mion');

-- ----------------------------
-- Table structure for m_spareparts
-- ----------------------------
DROP TABLE IF EXISTS `m_spareparts`;
CREATE TABLE `m_spareparts`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name_spare` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `harga` int(255) NULL DEFAULT NULL,
  `kode` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of m_spareparts
-- ----------------------------
INSERT INTO `m_spareparts` VALUES (1, 'Busy', 7000, 'SP01');
INSERT INTO `m_spareparts` VALUES (2, 'Velg', 80000, 'SP02');
INSERT INTO `m_spareparts` VALUES (3, 'Ruji', 92000, 'SP03');
INSERT INTO `m_spareparts` VALUES (4, 'Tromol', 85000, 'SP04');

-- ----------------------------
-- Table structure for t_services
-- ----------------------------
DROP TABLE IF EXISTS `t_services`;
CREATE TABLE `t_services`  (
  `no_services` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `tgl` date NULL DEFAULT NULL,
  `total` int(255) NULL DEFAULT NULL,
  `kd_pel` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`no_services`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_services
-- ----------------------------
INSERT INTO `t_services` VALUES ('ser001', '2020-05-16', 192200, 'p01');
INSERT INTO `t_services` VALUES ('SER002', '2020-05-16', 13300, 'P01');

-- ----------------------------
-- Table structure for transaksi
-- ----------------------------
DROP TABLE IF EXISTS `transaksi`;
CREATE TABLE `transaksi`  (
  `no_services` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `kode_spare` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `jml_item` int(255) NULL DEFAULT NULL,
  `discount` bigint(255) NULL DEFAULT NULL,
  `jml_bayar` bigint(255) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of transaksi
-- ----------------------------
INSERT INTO `transaksi` VALUES ('SER001', 'SP01', 4, 5, 26600);
INSERT INTO `transaksi` VALUES ('SER001', 'SP03', 2, 10, 165600);
INSERT INTO `transaksi` VALUES ('SER002', 'SP01', 2, 5, 13300);

SET FOREIGN_KEY_CHECKS = 1;
