# KafkaDemo

Bu proje, Spring Boot ve Apache Kafka kullanarak mikroservisler arası iletişim örneği olarak geliştirilmiştir. Projede, `KafkaDemo` ve `StorageApp` olmak üzere iki farklı yapı tek bir Spring Boot uygulaması içerisinde modüler olarak ayrılmıştır.

İlişkili repo:
- [StorageApp](https://github.com/mabattal/storageApp)

## 🧰 Kullanılan Teknolojiler

- Java 21
- Spring Boot
- Spring Web
- Spring Kafka
- Spring Data JPA
- Apache Kafka
- PostgreSQL
- Lombok
- Maven

## 🔄 Retry ve DLT Mekanizması

- Kafka mesajı işlenemezse 3 kez tekrar denenir (FixedBackOff).
- Yine de başarısız olursa photo-upload.DLT topic’ine gönderilir.

## 📁 Kafka Topic Yapısı
- ⬅️ photo-upload
- ➡️ photo-upload.DLT : Hatalı mesajların düştüğü Dead Letter Topic.


## 🧩 Modüller

### 1. KafkaDemo
- REST API ile kullanıcıdan fotoğraf alır.
- Fotoğraf verisini Kafka REST API ile Storage Servise gönderir.
- Kafka'dan okuduğu dosya yolu bilgisini veritabanına kaydeder.
- Kafka consumer olarak çalışır.

### 2. StorageApp
- Upload Servisten gelen fotoğraf verisini işler ve dosya sistemine kaydeder.
- Dosya yolu bilgisini kafka'ya gönderir.
- Kafka producer olarak çalışır.
