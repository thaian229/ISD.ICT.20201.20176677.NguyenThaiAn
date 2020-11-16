drop table if exists Book;

drop table if exists CD;

drop table if exists DVD;

drop table if exists OrderMedia;

drop table if exists PaymentTransaction;

drop table if exists Card;

drop table if exists Invoice;

drop table if exists RushOrder;

drop table if exists RushOrderMedia;

drop table if exists Media;

drop table if exists "Order";

drop table if exists DeliveryInfo;

BEGIN;
CREATE TABLE "Media"
(
    "id"         INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    "category"   VARCHAR(45)                       NOT NULL,
    "price"      INTEGER                           NOT NULL,
    "quantity"   INTEGER                           NOT NULL,
    "title"      VARCHAR(45)                       NOT NULL,
    "value"      INTEGER                           NOT NULL,
    "imageUrl"   VARCHAR(45)                       NOT NULL,
    "isRushable" BOOLEAN                           NOT NULL
);
CREATE TABLE "CD"
(
    "id"           INTEGER PRIMARY KEY NOT NULL,
    "artist"       VARCHAR(45)         NOT NULL,
    "recordLabel"  VARCHAR(45)         NOT NULL,
    "musicType"    VARCHAR(45)         NOT NULL,
    "releasedDate" DATE,
    CONSTRAINT "fk_CD_Media1"
        FOREIGN KEY ("id")
            REFERENCES "Media" ("id")
);
CREATE TABLE "Book"
(
    "id"           INTEGER PRIMARY KEY NOT NULL,
    "author"       VARCHAR(45)         NOT NULL,
    "coverType"    VARCHAR(45)         NOT NULL,
    "publisher"    VARCHAR(45)         NOT NULL,
    "publishDate"  DATETIME            NOT NULL,
    "numOfPages"   INTEGER             NOT NULL,
    "language"     VARCHAR(45)         NOT NULL,
    "bookCategory" VARCHAR(45)         NOT NULL,
    CONSTRAINT "fk_Book_Media1"
        FOREIGN KEY ("id")
            REFERENCES "Media" ("id")
);
CREATE TABLE "DeliveryInfo"
(
    "id"           INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    "name"         VARCHAR(45),
    "province"     VARCHAR(45),
    "instructions" VARCHAR(200),
    "address"      VARCHAR(100)
);
CREATE TABLE "Card"
(
    "id"          INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    "cardCode"    VARCHAR(15)                       NOT NULL,
    "owner"       VARCHAR(45)                       NOT NULL,
    "cvvCode"     VARCHAR(3)                        NOT NULL,
    "dateExpired" VARCHAR(4)                        NOT NULL
);
CREATE TABLE "DVD"
(
    "id"           INTEGER PRIMARY KEY NOT NULL,
    "discType"     VARCHAR(45)         NOT NULL,
    "director"     VARCHAR(45)         NOT NULL,
    "runtime"      INTEGER             NOT NULL,
    "studio"       VARCHAR(45)         NOT NULL,
    "subtitle"     VARCHAR(45)         NOT NULL,
    "releasedDate" DATETIME,
    CONSTRAINT "fk_DVD_Media1"
        FOREIGN KEY ("id")
            REFERENCES "Media" ("id")
);
CREATE TABLE "Order"
(
    "id"             INTEGER NOT NULL,
    "shippingFees"   VARCHAR(45),
    "DeliveryInfoId" INTEGER NOT NULL,
    PRIMARY KEY ("id", "DeliveryInfoId"),
    CONSTRAINT "fk_Order_DeliveryInfo1"
        FOREIGN KEY ("DeliveryInfoId")
            REFERENCES "DeliveryInfo" ("id")
);
CREATE INDEX "Order.fk_Order_DeliveryInfo1_idx" ON "Order" ("DeliveryInfoId");
CREATE TABLE "OrderMedia"
(
    "orderID"  INTEGER NOT NULL,
    "price"    INTEGER NOT NULL,
    "quantity" INTEGER NOT NULL,
    "mediaId"  INTEGER NOT NULL,
    PRIMARY KEY ("orderID", "mediaId"),
    CONSTRAINT "fk_ordermedia_order"
        FOREIGN KEY ("orderID")
            REFERENCES "Order" ("id"),
    CONSTRAINT "fk_OrderMedia_Media1"
        FOREIGN KEY ("mediaId")
            REFERENCES "Media" ("id")
);
CREATE INDEX "OrderMedia.fk_ordermedia_order_idx" ON "OrderMedia" ("orderID");
CREATE INDEX "OrderMedia.fk_OrderMedia_Media1_idx" ON "OrderMedia" ("mediaId");
CREATE TABLE "RushOrder"
(
    "id"             INTEGER   NOT NULL,
    "deliveryTime"   TIMESTAMP NOT NULL,
    "shippingFees"   VARCHAR(45),
    "deliveryInfoId" INTEGER   NOT NULL,
    PRIMARY KEY ("id", "deliveryInfoId"),
    CONSTRAINT "fk_RushOrder_DeliveryInfo1"
        FOREIGN KEY ("deliveryInfoId")
            REFERENCES "DeliveryInfo" ("id")
);
CREATE TABLE "RushOrderMedia"
(
    "rushOrderID" INTEGER NOT NULL,
    "mediaId"     INTEGER NOT NULL,
    "price"       INTEGER NOT NULL,
    "quantity"    INTEGER NOT NULL,
    PRIMARY KEY ("rushOrderID", "mediaId"),
    CONSTRAINT "fk_rushOrdermedia_rushOrder"
        FOREIGN KEY ("rushOrderID")
            REFERENCES "RushOrder" ("id"),
    CONSTRAINT "fk_RushOrderMedia_Media1"
        FOREIGN KEY ("mediaId")
            REFERENCES "Media" ("id")
);
CREATE INDEX "RushOrderMedia.fk_rushOrdermedia_rushOrder_idx" ON "RushOrderMedia" ("rushOrderID");
CREATE INDEX "RushOrderMedia.fk_RushOrderMedia_Media1_idx" ON "RushOrderMedia" ("mediaId");
CREATE TABLE "Invoice"
(
    "id"          INTEGER PRIMARY KEY NOT NULL,
    "totalAmount" INTEGER             NOT NULL,
    "orderId"     INTEGER,
    "rushOrderId" INTEGER,
    CONSTRAINT "fk_Invoice_Order1"
        FOREIGN KEY ("orderId")
            REFERENCES "Order" ("id"),
    CONSTRAINT "fk_Invoice_RushOrder1"
        FOREIGN KEY ("rushOrderId")
            REFERENCES "RushOrder" ("id")

);
CREATE INDEX "Invoice.fk_Invoice_Order1_idx" ON "Invoice" ("orderId");
CREATE INDEX "Invoice.fk_Invoice_RushOrder1_idx" ON "Invoice" ("rushOrderId");
CREATE TABLE "PaymentTransaction"
(
    "id"        INTEGER     NOT NULL,
    "createAt"  DATETIME    NOT NULL,
    "content"   VARCHAR(45) NOT NULL,
    "method"    VARCHAR(45),
    "cardId"    INTEGER     NOT NULL,
    "invoiceId" INTEGER     NOT NULL,
    PRIMARY KEY ("id", "cardId", "invoiceId"),
    CONSTRAINT "fk_PaymentTransaction_Card1"
        FOREIGN KEY ("cardId")
            REFERENCES "Card" ("id"),
    CONSTRAINT "fk_PaymentTransaction_Invoice1"
        FOREIGN KEY ("invoiceId")
            REFERENCES "Invoice" ("id")
);
CREATE INDEX "PaymentTransaction.fk_PaymentTransaction_Card1_idx" ON
    "PaymentTransaction" ("cardId");
CREATE INDEX "PaymentTransaction.fk_PaymentTransaction_Invoice1_idx"
    ON "PaymentTransaction" ("invoiceId");
COMMIT;