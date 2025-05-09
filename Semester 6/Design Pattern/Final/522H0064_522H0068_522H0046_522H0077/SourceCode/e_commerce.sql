-- phpMyAdmin SQL Dump
-- version 5.2.2
-- https://www.phpmyadmin.net/
--
-- Máy chủ: mysql
-- Thời gian đã tạo: Th4 28, 2025 lúc 03:59 PM
-- Phiên bản máy phục vụ: 9.2.0
-- Phiên bản PHP: 8.2.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `e_commerce`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `accounts`
--

CREATE TABLE `accounts` (
  `id` int NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `locked` bit(1) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role_id` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `accounts`
--

INSERT INTO `accounts` (`id`, `email`, `locked`, `password`, `role_id`) VALUES
(1, 'admin@example.com', b'0', '$2a$10$3QeUyRwjPfot6Vh1qV6RYuA056IOrHKpSJZPjJDRPoUh7SBBxtYEO', -499564378),
(2, 'apple@supplier.com', b'0', '$2a$10$1tmGTYlJJOnVSQv84UIHFupn/VkUkeJ.F6UKhRJv09830sKWjkMLK', 1872970900),
(3, 'samsung@supplier.com', b'0', '$2a$10$bDJ26AQJ7wh0S8k.hzGAo.QRpllhq5UMBNm78LYtPXaIzEP7ng.AO', 1872970900),
(4, 'sony@supplier.com', b'0', '$2a$10$5OrsY/Jj3ECpwnKsxd.A4u79c5HeeWcSkV4FBgU3g5EnEn6iZMt6m', 1872970900),
(5, 'lg@supplier.com', b'0', '$2a$10$bRnMmJgO87qqCByVE0Ya8uPmn6m2rJximEspV2ex1OGN7tpH/HaO.', 1872970900),
(6, 'dell@supplier.com', b'0', '$2a$10$WM5fRd58cGFfxUTNxVSvkeeZiwrlDKFG8iwOV0C1YHikaVz1jPeUu', 1872970900),
(7, 'xiaomi@supplier.com', b'0', '$2a$10$/jUZ2nygZSWiR11CpeuC0OupKmVHS8nKRzFWhyr1YYkPikGLWWL1a', 1872970900),
(8, 'khanhmh2004@gmail.com', b'0', '$2a$10$6bdlWkhBllj3TOI4dqRqN.aGrp9fQtC6IRQRSfpsM9RuqCHWCKLMa', -820557183),
(9, 'phucpham.1803@gmail.com', b'0', '$2a$10$Oa9zth6N5zGxEFpQmqv1KeQl2jUizrNegVfWPLs5Q/RFbRjANZl7W', -820557183);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `carts`
--

CREATE TABLE `carts` (
  `id_cart` int NOT NULL,
  `account_id` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `carts`
--

INSERT INTO `carts` (`id_cart`, `account_id`) VALUES
(-57718834, 1),
(-1504623566, 8),
(-978171992, 9);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `cart_details`
--

CREATE TABLE `cart_details` (
  `id_cart_detail` int NOT NULL,
  `date_cart_detail` datetime(6) DEFAULT NULL,
  `quantity_item_cartdetail` int DEFAULT NULL,
  `cart_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `deliveries`
--

CREATE TABLE `deliveries` (
  `delivery_id` bigint NOT NULL,
  `info` varchar(255) DEFAULT NULL,
  `supplier_id` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `images`
--

CREATE TABLE `images` (
  `image_id` int NOT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `product_id` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `images`
--

INSERT INTO `images` (`image_id`, `image_url`, `product_id`) VALUES
(9, 'http://res.cloudinary.com/dswwzexhq/image/upload/v1745423303/vpukl07ql9rubfqsseyn.webp', 1),
(10, 'http://res.cloudinary.com/dswwzexhq/image/upload/v1745423305/qixiy7lhankvwmtydml5.webp', 1),
(11, 'http://res.cloudinary.com/dswwzexhq/image/upload/v1745423709/k6awpw4rwwlrrrorf90b.webp', 2),
(12, 'http://res.cloudinary.com/dswwzexhq/image/upload/v1745423711/njzlb1lkmedims68oe8g.webp', 2),
(13, 'http://res.cloudinary.com/dswwzexhq/image/upload/v1745423713/lyfwttfpxaowsj4zg1yf.webp', 2),
(14, 'http://res.cloudinary.com/dswwzexhq/image/upload/v1745423871/cuws1bngby6i1wpv3sae.webp', 3),
(15, 'http://res.cloudinary.com/dswwzexhq/image/upload/v1745423872/ownq0l6qkfjig3ffpilb.webp', 3),
(16, 'http://res.cloudinary.com/dswwzexhq/image/upload/v1745423874/bqyezndmmrbsq0f2kwdw.webp', 3),
(17, 'http://res.cloudinary.com/dswwzexhq/image/upload/v1745425220/r8jzyace8gkjftujcnvm.webp', 4),
(18, 'http://res.cloudinary.com/dswwzexhq/image/upload/v1745425223/tjgmtl0dzqqrbdgtvl7c.webp', 4),
(19, 'http://res.cloudinary.com/dswwzexhq/image/upload/v1745425365/b5pgd8ht09htdxgmuumm.webp', 5),
(20, 'http://res.cloudinary.com/dswwzexhq/image/upload/v1745425368/xaagm6ogptjo0bflyo8w.webp', 5),
(21, 'http://res.cloudinary.com/dswwzexhq/image/upload/v1745425595/ht0tvkhdhfzyv6cepagi.webp', 6),
(22, 'http://res.cloudinary.com/dswwzexhq/image/upload/v1745425596/tjzp7tfrljojtte1ilab.webp', 6),
(23, 'http://res.cloudinary.com/dswwzexhq/image/upload/v1745425771/leo1uzf1gizuybda6rjs.webp', 7),
(24, 'http://res.cloudinary.com/dswwzexhq/image/upload/v1745425773/uafm2ahis4ceaulgwh8y.webp', 7),
(25, 'http://res.cloudinary.com/dswwzexhq/image/upload/v1745425997/iwitk6kamepe0fjgl28e.webp', 8),
(26, 'http://res.cloudinary.com/dswwzexhq/image/upload/v1745425999/lv7n7htc198pacezggmt.webp', 8),
(27, 'http://res.cloudinary.com/dswwzexhq/image/upload/v1745426194/oym5yh1audbgtmpawz7n.webp', 9),
(28, 'http://res.cloudinary.com/dswwzexhq/image/upload/v1745426196/zrlueshm7bgyadgtcdyp.avif', 9),
(29, 'http://res.cloudinary.com/dswwzexhq/image/upload/v1745426357/vjhy59slugjq6inq2a9o.webp', 10),
(30, 'http://res.cloudinary.com/dswwzexhq/image/upload/v1745426360/dviromlw4bh7zp6aqugd.png', 10),
(31, 'http://res.cloudinary.com/dswwzexhq/image/upload/v1745426498/myjfuyqqyforuudppsbv.webp', 11),
(32, 'http://res.cloudinary.com/dswwzexhq/image/upload/v1745426500/s0usuj1hyta428nibnsk.webp', 11),
(33, 'http://res.cloudinary.com/dswwzexhq/image/upload/v1745426621/iv3bcibdrqpcb3huuxrm.webp', 12),
(34, 'http://res.cloudinary.com/dswwzexhq/image/upload/v1745426623/zbgz7xthwxreg2ruefrb.webp', 12),
(35, 'http://res.cloudinary.com/dswwzexhq/image/upload/v1745426746/a9cgdmnmffinpytvk2ux.jpg', 13),
(36, 'http://res.cloudinary.com/dswwzexhq/image/upload/v1745426749/wfzajbknkhaouznb3czf.jpg', 13),
(37, 'http://res.cloudinary.com/dswwzexhq/image/upload/v1745426924/jzoynzmugb9f1y8jx3s5.jpg', 14),
(38, 'http://res.cloudinary.com/dswwzexhq/image/upload/v1745426926/itqvp8nf6337jnpiubqs.webp', 14),
(39, 'http://res.cloudinary.com/dswwzexhq/image/upload/v1745427059/cqkrtqf8rrs1p6r6rqwy.webp', 15),
(40, 'http://res.cloudinary.com/dswwzexhq/image/upload/v1745427060/yg8bnzgmlyx9mxbykvyy.webp', 15),
(41, 'http://res.cloudinary.com/dswwzexhq/image/upload/v1745427062/hxv0yxlbns1hyx8ddjvp.webp', 15),
(42, 'http://res.cloudinary.com/dswwzexhq/image/upload/v1745427247/ihbirrnlbwkhextwm3ko.jpg', 16),
(43, 'http://res.cloudinary.com/dswwzexhq/image/upload/v1745427249/t8leqdvrlz2kq30unnlf.jpg', 16),
(44, 'http://res.cloudinary.com/dswwzexhq/image/upload/v1745459950/nlqvajsw5gvq9ex9rhhq.png', 17),
(45, 'http://res.cloudinary.com/dswwzexhq/image/upload/v1745573231/zwyjzjlicp2hhbyao9mt.webp', 18);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `invoice`
--

CREATE TABLE `invoice` (
  `id` int NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `total_price` double DEFAULT NULL,
  `user_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `invoice`
--

INSERT INTO `invoice` (`id`, `created_at`, `total_price`, `user_id`) VALUES
(7, '2025-04-24 00:08:05.946000', 23774500, 3),
(8, '2025-04-24 09:04:01.065000', 25501.1, 3),
(9, '2025-04-24 14:14:30.837000', 34114500, 3),
(10, '2025-04-24 14:23:26.203000', 118785000, 2),
(12, '2025-04-25 16:26:07.486000', 47538000, 3),
(17, '2025-04-22 01:57:19.101000', 28212.6, 3);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `invoice_item`
--

CREATE TABLE `invoice_item` (
  `id` int NOT NULL,
  `price` double NOT NULL,
  `product_id` int DEFAULT NULL,
  `product_name` varchar(255) DEFAULT NULL,
  `quantity` int NOT NULL,
  `invoice_id` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `invoice_item`
--

INSERT INTO `invoice_item` (`id`, `price`, `product_id`, `product_name`, `quantity`, `invoice_id`) VALUES
(1, 1233, 1, 'haha', 3, 7),
(2, 1233, 1, 'haha', 2, 8),
(3, 1233, 1, 'haha', 1, 10),
(4, 23, 4, 'adf', 3, 9),
(5, 1233, 1, 'haha', 2, 17),
(6, 21590000, 1, 'Apple MacBook Air M2 2024', 1, 7),
(7, 1, 17, 'Test', 1, 8),
(8, 30990000, 8, 'iPhone 16 Pro Max', 1, 9),
(9, 21590000, 1, 'Apple MacBook Air M2 2024', 5, 10),
(10, 21590000, 1, 'Apple MacBook Air M2 2024', 2, 12);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `orders`
--

CREATE TABLE `orders` (
  `id_order` int NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `date_create_order` datetime(6) DEFAULT NULL,
  `fullname` varchar(255) DEFAULT NULL,
  `payment_method` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `quantity_item_order` int DEFAULT NULL,
  `total_price_order` decimal(38,2) DEFAULT NULL,
  `id_order_status` int DEFAULT NULL,
  `id_supplier` int DEFAULT NULL,
  `id_user` bigint DEFAULT NULL,
  `is_reviewed` bit(1) NOT NULL,
  `shipping_fee` decimal(38,2) DEFAULT NULL,
  `tax_fee` decimal(38,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `orders`
--

INSERT INTO `orders` (`id_order`, `address`, `date_create_order`, `fullname`, `payment_method`, `phone`, `quantity_item_order`, `total_price_order`, `id_order_status`, `id_supplier`, `id_user`, `is_reviewed`, `shipping_fee`, `tax_fee`) VALUES
(7, '123, Huyện Phù Cừ, Hưng Yên', '2025-04-24 00:05:52.679435', '123', 'E-Wallet', '0358948134', 1, 23774500.00, 2, 1, 3, b'0', 25500.00, 2159000.00),
(8, '123, Huyện Phù Cừ, Hưng Yên', '2025-04-24 09:03:44.010707', 'Test', 'E-Wallet', '0358948134', 1, 25501.10, 2, 2, 3, b'1', 25500.00, 0.10),
(9, '1221212121, Huyện Thuận Bắc, Ninh Thuận', '2025-04-24 14:13:29.144333', 'Nnguyen Duc Huy', 'E-Wallet', '1234567890', 1, 34114500.00, 2, 1, 3, b'1', 25500.00, 3099000.00),
(10, '18, Thành phố Tân An, Long An', '2025-04-24 14:22:14.281072', 'khanh', 'E-Wallet', '0357853366', 5, 118785000.00, 2, 1, 2, b'1', 40000.00, 10795000.00),
(11, '123, Huyện Phù Cừ, Hưng Yên', '2025-04-25 15:46:48.218768', '123', NULL, '0358948124', 1, 29384500.00, 1, 1, 3, b'0', 25500.00, 2669000.00),
(12, 'sgdf, Huyện Phù Cừ, Hưng Yên', '2025-04-25 16:24:54.080322', 'pkucpkam', 'Bank Transfer', '03447847333', 2, 47538000.00, 2, 1, 3, b'1', 40000.00, 4318000.00);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `order_details`
--

CREATE TABLE `order_details` (
  `id_orderdetail` int NOT NULL,
  `quantity_item_cartdetail` int DEFAULT NULL,
  `totalprice_cartdetail` decimal(38,2) DEFAULT NULL,
  `unitprice_cartdetail` decimal(38,2) DEFAULT NULL,
  `id_order` int DEFAULT NULL,
  `id_product` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `order_details`
--

INSERT INTO `order_details` (`id_orderdetail`, `quantity_item_cartdetail`, `totalprice_cartdetail`, `unitprice_cartdetail`, `id_order`, `id_product`) VALUES
(1, 1, 21590000.00, 21590000.00, 7, 1),
(2, 1, 1.00, 1.00, 8, 17),
(3, 1, 30990000.00, 30990000.00, 9, 8),
(4, 5, 107950000.00, 21590000.00, 10, 1),
(5, 1, 26690000.00, 26690000.00, 11, 2),
(6, 2, 43180000.00, 21590000.00, 12, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `order_status`
--

CREATE TABLE `order_status` (
  `id` int NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `order_status`
--

INSERT INTO `order_status` (`id`, `name`) VALUES
(1, 'PENDING'),
(2, 'PAID'),
(3, 'CANCELLED');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `products`
--

CREATE TABLE `products` (
  `dtype` varchar(31) NOT NULL,
  `product_id` int NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `price` decimal(38,2) NOT NULL,
  `product_name` varchar(255) NOT NULL,
  `quantity` int NOT NULL,
  `status_activity` bit(1) DEFAULT NULL,
  `status_verify` bit(1) DEFAULT NULL,
  `battery_life` int DEFAULT NULL,
  `is_wireless` bit(1) DEFAULT NULL,
  `noise_cancellation` varchar(255) DEFAULT NULL,
  `cpu` varchar(255) DEFAULT NULL,
  `ram` int DEFAULT NULL,
  `storage` int DEFAULT NULL,
  `cameramp` int DEFAULT NULL,
  `screen_size` varchar(255) DEFAULT NULL,
  `hasgps` bit(1) DEFAULT NULL,
  `water_resistant` bit(1) DEFAULT NULL,
  `has_pen_support` bit(1) DEFAULT NULL,
  `product_type_id` int NOT NULL,
  `supplier_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `products`
--

INSERT INTO `products` (`dtype`, `product_id`, `description`, `price`, `product_name`, `quantity`, `status_activity`, `status_verify`, `battery_life`, `is_wireless`, `noise_cancellation`, `cpu`, `ram`, `storage`, `cameramp`, `screen_size`, `hasgps`, `water_resistant`, `has_pen_support`, `product_type_id`, `supplier_id`) VALUES
('Laptop', 1, 'Máy mới 100%, đầy đủ phụ kiện từ nhà sản xuất. Sản phẩm có mã SA/A (được Apple Việt Nam phân phối chính thức).', 21590000.00, 'Apple MacBook Air M2 2024', 2, b'1', b'1', NULL, NULL, NULL, 'Apple M2 8 nhân', 16, 256, NULL, NULL, NULL, NULL, NULL, 1, 1),
('Laptop', 2, 'Máy mới 100%, đầy đủ phụ kiện từ nhà sản xuất. Sản phẩm có mã SA/A (được Apple Việt Nam phân phối chính thức).', 26690000.00, 'MacBook Air M4 13 inch 2025 ', 9, b'1', b'1', NULL, NULL, NULL, 'CPU 10 lõi với 4 lõi hiệu năng và 6 lõi tiết kiệm điện', 16, 256, NULL, NULL, NULL, NULL, NULL, 1, 1),
('Laptop', 3, 'Máy mới 100%, đầy đủ phụ kiện từ nhà sản xuất. Sản phẩm có mã SA/A (được Apple Việt Nam phân phối chính thức).', 83990000.00, 'MacBook Pro 16 M3 Max', 5, b'1', b'1', NULL, NULL, NULL, 'Apple M3 Max 16 nhân', 48, 1048, NULL, NULL, NULL, NULL, NULL, 1, 1),
('Laptop', 4, 'Laptop gaming Dell G15 5530 là sản phẩm nằm trong phân khúc trên laptop gaming cao cấp trên 30 triệu và là thế hệ laptop gaming tiên tiến hơn so với các mẫu laptop tiền nhiệm của Dell.', 40490000.00, 'Laptop gaming Dell G15 5530', 10, b'1', b'1', NULL, NULL, NULL, 'Intel® Core™ i9-13900HX', 16, 1048, NULL, NULL, NULL, NULL, NULL, 1, 5),
('Laptop', 5, 'Dell Inspiron 16 5640 là chiếc laptop mỏng nhẹ phù hợp cho các bạn học sinh, sinh viên, dân văn phòng nhu cầu cần một chiếc laptop đáp ứng tốt tất cả các tác vụ cơ bản, học tập, giải trí.', 25790000.00, 'Laptop Dell Inspiron 16 5640', 5, b'1', b'1', NULL, NULL, NULL, 'Intel® Core™ 7 150U', 16, 1048, NULL, NULL, NULL, NULL, NULL, 1, 5),
('Headphone', 6, 'Tai nghe không dây Xiaomi Redmi Buds 6 Pro trang bị thiết kế dạng in-ear nhằm đảm bảo trải nghiệm đeo chắc chắn khi dùng trong các hoạt động thể chất. Mẫu tai nghe không dây này được trang bị 3 micro AI với khả năng lọc gió lên tới 12m/s.', 1990000.00, 'Xiaomi Redmi Buds 6 Pro', 10, b'1', b'1', 36, b'1', 'Chống ồn chủ động 55dB/4kHz với 20 chế độ chống ồn', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 4, 6),
('Headphone', 7, 'Thiết kế chuẩn công thái học phong cách, ôm khít vào tai, hạn chế rơi khi di chuyển. Đắm chìm trong không gian âm nhạc riêng với tính năng chống ồn ANC và 360 Audio', 2590000.00, 'Samsung Galaxy Buds2 Pro', 5, b'1', b'1', 18, b'1', 'Chống ồn chủ động (Active Noise Canceling)', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 4, 2),
('Phone', 8, 'iPhone 16 Pro Max sở hữu màn hình Super Retina XDR OLED 6.9 inch với công nghệ ProMotion, mang lại trải nghiệm hiển thị mượt mà và sắc nét, lý tưởng cho giải trí và làm việc.', 30990000.00, 'iPhone 16 Pro Max', 3, b'1', b'1', 12, NULL, NULL, NULL, NULL, NULL, 48, '6.9', NULL, NULL, NULL, 3, 1),
('Phone', 9, 'Samsung S25 Ultra mạnh mẽ với chip Snapdragon 8 Elite For Galaxy mới nhất, RAM 12GB và bộ nhớ trong 256GB-1TB. Hệ thống 3 camera sau chất lượng gồm camera chính 200MP, camera tele 50MP và camera góc siêu rộng 50MP.', 28990000.00, 'Samsung Galaxy S25 Ultra', 10, b'1', b'1', 20, NULL, NULL, NULL, NULL, NULL, 50, '6.9', NULL, NULL, NULL, 3, 2),
('Phone', 10, 'Xiaomi 15 sở hữu màn hình CrystalRes AMOLED 6.36 inch, độ phân giải 1200 x 2670 pixels, hỗ trợ 68 tỷ màu, công nghệ HDR10+ và tốc độ làm mới 120Hz.', 21990000.00, 'Xiaomi 15 5G', 5, b'1', b'1', 24, NULL, NULL, NULL, NULL, NULL, 50, '6.36', NULL, NULL, NULL, 3, 6),
('SmartWatch', 11, 'Apple Watch SE 2 2024 40mm viền nhôm dây cao su mang đến thiết kế mới mẻ với kích thước 40mm cùng bộ kính cường lực Ion-X cứng cáp.', 5190000.00, 'Apple Watch SE 2 2024', 5, b'1', b'1', 18, NULL, NULL, NULL, NULL, NULL, NULL, NULL, b'1', b'1', NULL, 5, 1),
('SmartWatch', 12, 'Vòng đeo tay thông minh Xiaomi Mi Band 9 được trang bị viên pin 233 mAh, cung cấp thời lượng sử dụng có thể lên đến 21 ngày thoải mái để yên tâm sử dụng.', 790000.00, ' Xiaomi Mi Band 9', 10, b'1', b'1', 216, b'0', NULL, NULL, NULL, NULL, NULL, NULL, b'1', b'0', NULL, 5, 6),
('SmartWatch', 13, 'Samsung Galaxy Watch Ultra trang bị bộ vi xử lý tiến trình 3nm, 5 lõi mạnh mẽ, cung cấp hiệu năng mượt mà cho mọi tác vụ. Về khả năng hiển thị, Watch Ultra nổi bật với màn hình Super AMOLED độ sáng lên đến 3000 nits.', 13590000.00, 'Samsung Galaxy Watch Ultra', 10, b'1', b'1', 80, NULL, NULL, NULL, NULL, NULL, NULL, NULL, b'1', b'1', NULL, 5, 2),
('Tablet', 14, 'iPad Pro M4 là mẫu máy tính bảng cao cấp và chuyên nghiệp với chip M4 cho hiệu năng mạnh mẽ vượt bậc. Thế hệ iPad Pro mới còn sở hữu thiết kế mới mảnh mai hơn cùng màn hình Ultra Retina XDR siêu đẹp mắt.', 27790000.00, 'iPad Pro M4', 5, b'1', b'1', 12, NULL, NULL, NULL, NULL, NULL, NULL, '11', NULL, NULL, b'1', 2, 1),
('Tablet', 15, 'Xiaomi Pad 7 Pro là một chiếc máy tính bảng cao cấp có kích thước 251.2 x 173.4 x 6.18 mm, trọng lượng chỉ 500g siêu nhẹ, với thiết kế mặt kính, khung nhôm và mặt lưng nhôm.', 12990000.00, 'Xiaomi Pad 7 Pro', 10, b'1', b'1', 36, NULL, NULL, NULL, NULL, NULL, NULL, '12', NULL, NULL, b'1', 2, 6),
('Tablet', 16, 'Samsung Galaxy Tab A9 Plus wifi là phân khúc tablet mạnh mẽ của Samsung nổi bật với màn hình rộng lên đến 11 inch, cung cấp trải nghiệm hình ảnh tuyệt vời. Máy sở hữu viên Pin khủng với dung lượng 7040 mAh.', 4290000.00, 'Samsung Galaxy Tab A9+', 10, b'1', b'1', 36, NULL, NULL, NULL, NULL, NULL, NULL, '11', NULL, NULL, b'0', 2, 2),
('Headphone', 17, 'Test', 1.00, 'Test', 0, b'0', b'1', 12, b'1', 'Chống ồn chủ động 55dB/4kHz với 20 chế độ chống ồn', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 4, 2),
('Laptop', 18, 'ok', 123.00, 'test', 111, b'1', b'1', NULL, NULL, NULL, 'CPU 10 lõi với 4 lõi hiệu năng và 6 lõi tiết kiệm điện', 12, 256, NULL, NULL, NULL, NULL, NULL, 1, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `product_types`
--

CREATE TABLE `product_types` (
  `product_type_id` int NOT NULL,
  `product_type_name` enum('HEADPHONE','LAPTOP','PHONE','SMARTWATCH','TABLET') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `product_types`
--

INSERT INTO `product_types` (`product_type_id`, `product_type_name`) VALUES
(4, 'HEADPHONE'),
(1, 'LAPTOP'),
(3, 'PHONE'),
(5, 'SMARTWATCH'),
(2, 'TABLET');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `reviews`
--

CREATE TABLE `reviews` (
  `id` int NOT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `date` datetime(6) DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  `rating` int DEFAULT NULL,
  `user_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `reviews`
--

INSERT INTO `reviews` (`id`, `comment`, `date`, `product_id`, `rating`, `user_id`) VALUES
(1, 'Sản phẩm quá tuyệt với á chớ ', '2025-04-24 00:11:23.823000', 1, 5, 3),
(2, 'Test', '2025-04-24 09:04:25.028000', 17, 5, 3),
(5, '123', '2025-04-24 10:18:46.945000', 1, 5, 3),
(6, '123', '2025-04-24 10:21:44.610000', 1, 5, 3),
(7, 'oke', '2025-04-24 10:22:38.694000', 17, 1, 3),
(8, '123', '2025-04-24 10:25:45.208000', 17, 5, 3),
(9, 'ok', '2025-04-24 14:14:56.959000', 8, 4, 3),
(10, 'ngon', '2025-04-24 14:24:28.023000', 1, 4, 2),
(11, 'ok quia ne', '2025-04-25 16:26:36.918000', 1, 5, 3);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `roles`
--

CREATE TABLE `roles` (
  `id` int NOT NULL,
  `role` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `roles`
--

INSERT INTO `roles` (`id`, `role`) VALUES
(-820557183, 'user'),
(-499564378, 'admin'),
(1872970900, 'Supply');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `suppliers`
--

CREATE TABLE `suppliers` (
  `id` int NOT NULL,
  `supply_address` varchar(255) DEFAULT NULL,
  `supply_image` varchar(255) DEFAULT NULL,
  `supply_name` varchar(255) DEFAULT NULL,
  `supply_status` bit(1) DEFAULT NULL,
  `supply_status_verify` bit(1) DEFAULT NULL,
  `account_id` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `suppliers`
--

INSERT INTO `suppliers` (`id`, `supply_address`, `supply_image`, `supply_name`, `supply_status`, `supply_status_verify`, `account_id`) VALUES
(1, 'USA', NULL, 'APPLE', b'1', b'0', 2),
(2, 'KOREA', NULL, 'SAMSUNG', b'0', b'0', 3),
(3, 'USA', NULL, 'SONY', b'0', b'0', 4),
(4, 'KOREA', NULL, 'LG', b'0', b'0', 5),
(5, 'USA', NULL, 'DELL', b'0', b'0', 6),
(6, 'CHINA', NULL, 'XIAOMI', b'0', b'0', 7);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `id` bigint NOT NULL,
  `full_name` varchar(255) NOT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `account_id` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`id`, `full_name`, `phone`, `account_id`) VALUES
(1, 'Admin', '0123456789', 1),
(2, 'duy khanh', '0357853366', 8),
(3, 'Phuc Pham', '0358948134', 9);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `accounts`
--
ALTER TABLE `accounts`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKt3wava8ssfdspnh3hg4col3m1` (`role_id`);

--
-- Chỉ mục cho bảng `carts`
--
ALTER TABLE `carts`
  ADD PRIMARY KEY (`id_cart`),
  ADD UNIQUE KEY `UKhc9hwyept723lo4fhhylwmnmr` (`account_id`);

--
-- Chỉ mục cho bảng `cart_details`
--
ALTER TABLE `cart_details`
  ADD PRIMARY KEY (`id_cart_detail`),
  ADD KEY `FKkcochhsa891wv0s9wrtf36wgt` (`cart_id`),
  ADD KEY `FK9rlic3aynl3g75jvedkx84lhv` (`product_id`);

--
-- Chỉ mục cho bảng `deliveries`
--
ALTER TABLE `deliveries`
  ADD PRIMARY KEY (`delivery_id`),
  ADD KEY `FKkuv8b4k14w4x0tuld8nwt785f` (`supplier_id`);

--
-- Chỉ mục cho bảng `images`
--
ALTER TABLE `images`
  ADD PRIMARY KEY (`image_id`),
  ADD KEY `FKghwsjbjo7mg3iufxruvq6iu3q` (`product_id`);

--
-- Chỉ mục cho bảng `invoice`
--
ALTER TABLE `invoice`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `invoice_item`
--
ALTER TABLE `invoice_item`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKbu6tmpd0mtgu9wrw5bj5uv09v` (`invoice_id`);

--
-- Chỉ mục cho bảng `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id_order`),
  ADD KEY `FKbp6k71rsko970ooke8me82k9` (`id_order_status`),
  ADD KEY `FKj327vl229h9pchhkow14l8ejs` (`id_supplier`),
  ADD KEY `FKtb6jdc061vu6ydv1445lrigtb` (`id_user`);

--
-- Chỉ mục cho bảng `order_details`
--
ALTER TABLE `order_details`
  ADD PRIMARY KEY (`id_orderdetail`),
  ADD KEY `FK6h10g6el6eyicu33ddse0gm3v` (`id_order`),
  ADD KEY `FK41ypdnsfa4cd6poqkbthg94nc` (`id_product`);

--
-- Chỉ mục cho bảng `order_status`
--
ALTER TABLE `order_status`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`product_id`),
  ADD KEY `FKrv6og3b2qlahvka0bxn7btyqd` (`product_type_id`),
  ADD KEY `FK6i174ixi9087gcvvut45em7fd` (`supplier_id`);

--
-- Chỉ mục cho bảng `product_types`
--
ALTER TABLE `product_types`
  ADD PRIMARY KEY (`product_type_id`),
  ADD UNIQUE KEY `UKpmgxix0n8b3o2lfxperbhg2dk` (`product_type_name`);

--
-- Chỉ mục cho bảng `reviews`
--
ALTER TABLE `reviews`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `suppliers`
--
ALTER TABLE `suppliers`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK8mmtdyputbwgoumfisqbgafrh` (`account_id`);

--
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK1yov8c5ew74vlt8qra6cewq99` (`account_id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `accounts`
--
ALTER TABLE `accounts`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT cho bảng `cart_details`
--
ALTER TABLE `cart_details`
  MODIFY `id_cart_detail` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;

--
-- AUTO_INCREMENT cho bảng `deliveries`
--
ALTER TABLE `deliveries`
  MODIFY `delivery_id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `images`
--
ALTER TABLE `images`
  MODIFY `image_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=46;

--
-- AUTO_INCREMENT cho bảng `invoice_item`
--
ALTER TABLE `invoice_item`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT cho bảng `orders`
--
ALTER TABLE `orders`
  MODIFY `id_order` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT cho bảng `order_details`
--
ALTER TABLE `order_details`
  MODIFY `id_orderdetail` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT cho bảng `order_status`
--
ALTER TABLE `order_status`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `products`
--
ALTER TABLE `products`
  MODIFY `product_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT cho bảng `product_types`
--
ALTER TABLE `product_types`
  MODIFY `product_type_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `reviews`
--
ALTER TABLE `reviews`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT cho bảng `suppliers`
--
ALTER TABLE `suppliers`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT cho bảng `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Ràng buộc đối với các bảng kết xuất
--

--
-- Ràng buộc cho bảng `accounts`
--
ALTER TABLE `accounts`
  ADD CONSTRAINT `FKt3wava8ssfdspnh3hg4col3m1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`);

--
-- Ràng buộc cho bảng `carts`
--
ALTER TABLE `carts`
  ADD CONSTRAINT `FKpffuu6bbv6kg7qcawlwur1q1q` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`);

--
-- Ràng buộc cho bảng `cart_details`
--
ALTER TABLE `cart_details`
  ADD CONSTRAINT `FK9rlic3aynl3g75jvedkx84lhv` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`),
  ADD CONSTRAINT `FKkcochhsa891wv0s9wrtf36wgt` FOREIGN KEY (`cart_id`) REFERENCES `carts` (`id_cart`);

--
-- Ràng buộc cho bảng `deliveries`
--
ALTER TABLE `deliveries`
  ADD CONSTRAINT `FKkuv8b4k14w4x0tuld8nwt785f` FOREIGN KEY (`supplier_id`) REFERENCES `suppliers` (`id`);

--
-- Ràng buộc cho bảng `images`
--
ALTER TABLE `images`
  ADD CONSTRAINT `FKghwsjbjo7mg3iufxruvq6iu3q` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`);

--
-- Ràng buộc cho bảng `invoice_item`
--
ALTER TABLE `invoice_item`
  ADD CONSTRAINT `FKbu6tmpd0mtgu9wrw5bj5uv09v` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`id`);

--
-- Ràng buộc cho bảng `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `FKbp6k71rsko970ooke8me82k9` FOREIGN KEY (`id_order_status`) REFERENCES `order_status` (`id`),
  ADD CONSTRAINT `FKj327vl229h9pchhkow14l8ejs` FOREIGN KEY (`id_supplier`) REFERENCES `suppliers` (`id`),
  ADD CONSTRAINT `FKtb6jdc061vu6ydv1445lrigtb` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`);

--
-- Ràng buộc cho bảng `order_details`
--
ALTER TABLE `order_details`
  ADD CONSTRAINT `FK41ypdnsfa4cd6poqkbthg94nc` FOREIGN KEY (`id_product`) REFERENCES `products` (`product_id`),
  ADD CONSTRAINT `FK6h10g6el6eyicu33ddse0gm3v` FOREIGN KEY (`id_order`) REFERENCES `orders` (`id_order`);

--
-- Ràng buộc cho bảng `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `FK6i174ixi9087gcvvut45em7fd` FOREIGN KEY (`supplier_id`) REFERENCES `suppliers` (`id`),
  ADD CONSTRAINT `FKrv6og3b2qlahvka0bxn7btyqd` FOREIGN KEY (`product_type_id`) REFERENCES `product_types` (`product_type_id`);

--
-- Ràng buộc cho bảng `suppliers`
--
ALTER TABLE `suppliers`
  ADD CONSTRAINT `FKeu3e8ep76ocwsw9va5am899oq` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`);

--
-- Ràng buộc cho bảng `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `FKfm8rm8ks0kgj4fhlmmljkj17x` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
