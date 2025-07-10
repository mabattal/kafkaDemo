# KafkaDemo

Bu proje, Spring Boot ve Apache Kafka kullanarak mikroservisler arası iletişim örneği olarak geliştirilmiştir. Projede, `upload-service` ve `storage-service` olmak üzere iki farklı yapı tek bir Spring Boot uygulaması içerisinde modüler olarak ayrılmıştır.

İlişkili repo:
- [StorageApp](https://github.com/mabattal/storageApp)

## 🧰 Kullanılan Teknolojiler

- Java 21
- Spring Boot
- Spring Web
- Spring Kafka
- Apache Kafka
- PostgreSQL
- Lombok
- Maven

## 🧩 Modüller

### 1. Upload Service
- REST API ile kullanıcıdan fotoğraf alır.
- Fotoğraf verisini Kafka REST API ile Storage Servise gönderir.
- Kafka'dan okuduğu dosya yolu bilgisini veritabanına kaydeder.
- Kafka consumer olarak çalışır.

### 2. Storage Service
- Upload Servisten gelen fotoğraf verisini işler ve dosya sistemine kaydeder.
- Dosya yolu bilgisini kafka'ya gönderir.
- Kafka producer olarak çalışır.


## 📁 Kafka Topic Yapısı
- ⬅️ photo-upload
- ➡️ photo-upload.DLT : Hatalı mesajların düştüğü Dead Letter Topic.

