# AppTracer
A library to capture screen load times in mobile apps

**Performance matters**

Mobile apps are expected to be fast and smooth. Testing every device scenario using a lab of limited devices is quite impossible, so it is essential to collect real performance data from users to understand the expectations and set performance goals. 

**AppTracer** is a library that collects real-time screen load data from users. This allows understanding of issues that they might be facing and responding to them quickly. By linking AppTracer to a backend which serves a visual representation of data, it acts as a great benchmarking tool for apps. By default it uses [NewMonk](https://github.com/naukri-engineering/NewMonk) as its backend but it can easily attach to any of your backend systems. Your backend system must be robust enough to handle the volume of requests and data generated from your app while using this.

An application most of the time loads data from a webservice and the network latency and service performance affects the overall load time. With AppTracer, you can also capture the time taken by each and every web service call on a particular screen which will indicate how much of the total time was spent fetching data. All this data is grouped by device, app version, network type and OS details for quick diagnosis.

