# Streaming Platform Backend

A real-time synchronized streaming backend built using Java and Spring Boot.
This project started as a custom HTTP video streaming server using byte-range requests and is now evolving into a scalable HLS-based streaming architecture with cloud storage, CDN delivery, transcoding pipelines, and real-time watch-party synchronization.

---

# 🚀 Current Features

## ✅ HTTP Video Streaming

* Byte-range streaming using `RandomAccessFile`
* HTML5 video player support
* Seeking support
* Partial content delivery (`206 Partial Content`)

---

## ✅ WebSocket-based Real-Time Sync

* Built using Spring WebSocket
* Real-time room-based synchronization
* Event broadcasting system
* Session management

### Supported sync events

* Play
* Pause
* Seek
* Timestamp synchronization

---

## ✅ Room Architecture

* Multiple watch-party rooms
* Session → room mapping
* Room → sessions mapping
* Thread-safe room management
* Automatic room cleanup

---

## ✅ Backend Architecture Concepts Used

* Spring Boot
* Dependency Injection
* Singleton Services
* WebSocket Sessions
* Concurrent Data Structures
* REST APIs
* Real-time event handling

---

# 🧠 Current Architecture

```text
Frontend
   ↓
Spring Boot Backend
   ↓
-----------------------------------
| REST APIs                       |
| WebSocket Sync Engine           |
| Room Management                 |
-----------------------------------
   ↓
Video Streaming Layer
```

---

# 🔥 Upcoming Architecture Upgrade

The project is currently being migrated from simple byte-range streaming to a production-style HLS architecture.

---

# 📌 Planned Features

## 🎥 HLS Streaming

* `.m3u8` playlist generation
* Video chunking (`.ts` / `.m4s`)
* Adaptive bitrate streaming
* Multi-resolution transcoding

---

## ☁️ Cloud Migration

* AWS S3 for video storage
* CloudFront CDN integration
* Private bucket architecture
* Secure media delivery

---

## ⚙️ Transcoding Pipeline

* FFmpeg integration
* Multiple video resolutions
* Background processing

---

## 🐇 Queue-based Processing

* RabbitMQ integration
* Async transcoding jobs
* Worker-based processing pipeline

---

## ⚡ Caching Layer

* Redis integration
* Metadata caching
* Hot-content optimization

---

## 👥 Watch Party Features

* Live synchronized playback
* Shared rooms
* Real-time event broadcasting
* Future WebRTC integration for video calls

---

# 🧩 Future System Design

```text
Frontend (React)
        ↓
Spring Boot Backend
        ↓
-----------------------------------------
| Room Sync (WebSocket)                 |
| REST APIs                             |
| Upload Management                     |
| Metadata Service                      |
-----------------------------------------
        ↓
S3 Storage
        ↓
RabbitMQ Queue
        ↓
FFmpeg Workers
        ↓
HLS Chunks + Playlists
        ↓
CloudFront CDN
        ↓
Users
```

---

# 🛠️ Tech Stack

## Backend

* Java 17
* Spring Boot
* Spring WebSocket
* Maven

---

## Streaming

* HLS
* FFmpeg
* Byte-range Streaming

---

## Cloud & Infrastructure

* AWS S3
* AWS CloudFront
* RabbitMQ
* Redis

---

## Future Frontend

* ReactJS
* HTML5 Video Player
* WebSocket Client

---

# 📚 Concepts Explored

* HTTP Range Requests
* Adaptive Bitrate Streaming
* CDN Architecture
* Video Chunking
* Real-time Synchronization
* Concurrent Backend Design
* Asynchronous Processing
* Distributed Media Delivery

---

# 🎯 Project Goal

The long-term goal of this project is to build a scalable streaming and synchronized watch-party platform capable of:

* Adaptive video streaming
* Real-time synchronization
* Distributed media delivery
* Cloud-native deployment
* Couple watch-party experiences
* Integrated real-time communication

---

# 📈 Learning Focus

This project is also being used to deeply learn:

* Streaming system design
* Media delivery architecture
* Cloud infrastructure
* Backend scalability
* Real-time systems
* Distributed processing

---

# ⚠️ Current Status

The project is currently in active architecture transition:

* Moving from byte-range streaming → HLS
* Migrating to cloud-based media delivery
* Building async transcoding workflows
* Expanding real-time synchronization infrastructure

---

# 🚀 Next Milestones

* [ ] Generate HLS streams using FFmpeg
* [ ] Integrate AWS S3 uploads
* [ ] Add CloudFront CDN delivery
* [ ] Build RabbitMQ transcoding workers
* [ ] Add Redis caching
* [ ] Add React frontend
* [ ] Implement WebRTC video calls
* [ ] Deploy full stack to cloud

stributed media delivery and synchronization system"
```
