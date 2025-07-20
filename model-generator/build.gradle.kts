plugins {
    // Thay java-library thành java và application
    java
    application
}

group = "com.example"
version = "1.0-SNAPSHOT"



// Java 11
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation("net.sourceforge.jtds:jtds:1.3.1")
}



