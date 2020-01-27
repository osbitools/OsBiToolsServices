# OsBiToolsWsBase

## Project Description
*OsBiToools* is an open source project for data visualization on the Web (ONLY)  
Below is the short description of the project (full description can be 
found on http://www.osbitools.com/project-idea).

### What is *OsBiTools* means
*OsBiTools* = *OsBi* + *Tools*.  
*OsBi* is stands for **O**pen **S**ource **B**usiness **I**ntelligence.  
*Tools* helps understand that it set of components rather than single *server*.

Term *BI* (Business Intelligence) might be a bit confusing here 
because it's not primarily objective of this project.
However this project can be used as simple BI as well it can be used 
for real-tmie data visualization, GEO data visualization (in roadmap), 
for scientific purposes and so on.

### Do I need it ?
If you have some data in offline storage (SQL Database, CSV files, Excel etc.) and 
you need somehow visualize such data on the web (chart, tables, diagrams etc) than you 
definitely need to look & try this project because this is what (and why) it was build for.

### Brief Architecture

*OsBiTools* consists of 2 layers

1. **Web Services** – convert any data using predefined dataset maps into *JSON*
2. **Data Visualization** - converts *JSON* from previous layer into something visual (chart, diagrams, gauges etc.)

The whole process can be described by simple UML diagram below

![UML Diagram for Data Flow](http://www.osbitools.com/sites/default/files/pictures/uml_data_flow.png)

There is a reasonable question here: "Why double "conversion" required ? Why just convert data directly into image on the server side and deliver final picture to the client? Everyone doing that !!!". The answer is "because HTML5 allow image rendering inside browser and modern browser capable for such sort of tasks". Who is familiar with new HTML5 features it's a *canvas*. And this single feature completely changes modern way of delivering graphics to the client. Now it can be splits into 2 parts - delivering data to the client and actual graphic rendering on client's facility.

Second layer has weak relations with first one. If you have already JSON from third party source than you don't need a first layer. If you have already a good data visualization engine that only need *JSON* than you don't need a second layer. If you have a commercial BI software that cost you a lot and also causing you constant hassle when client comes with new requirement than you need both layers.

This is in brief overview of *OsBiTools* architecture. To prevent copy-paste of existing knowledge base please visit next links for general information about *OsBiTools*.

Project Idea (same is above but in details) -  http://www.osbitools.com/project-idea  
Blogs (installation, configuration) - http://www.osbitools.com/blog  
Forum - http://www.osbitools.com/forum  
FAQ - http://www.osbitools.com/faq

[1]: http://www.osbitools.com/blog/building-custom-web-widget


## About this project (**OsBiToolsWsBase**)

*OsBiToolsWsBase* is a set of SpringBoot based Web Services and shared libraries

- **OsBiWsCoreApp** is *Spring Boot* REST web microservice that converts external 
source of data into JSON format. 
This project is a core of "Web Services" layer. This service has no embedded security.
- **OsBiWsCoreShared**, **OsBiWsCoreAppShared** are shared library supporting 
REST core microservice.

- **OsBiWsMeApp** is *Spring Boot* REST back-end for Map Editor UI. 
This service has no embedded security.
- **OsBiWsMeShared**, **OsBiWsMeAppShared** are shared library supporting REST 
Map Editor service.

- **OsBiSharedUtils**, **OsBiWsSharedCommon**, **OsBiWsShared**, **OsBiWsDsShared**, 
**OsBiWsLsShared**, **OsBiWsWpShared**, **OsBiWsSharedRestBase**, **OsBiWsSharedRestWeb**,
**OsBiWsPrjSharedBase**, **OsBiWsPrjSharedRest**, **OsBiWsPrjSharedWeb** are 
shared resources and utilities for unit and integration testing of all project above

Package relationship shown on picture below:

![OsBiToolsSpringWs Packages](doc/OsBiToolsWsBase-Packages.png)

If you still have questions please don't hesitate ask questions on forum or use contact form on http://www.osbitools.com/contact-us for feedback.
