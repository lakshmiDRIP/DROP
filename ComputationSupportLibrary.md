
# Computation Support Library


<p align="center"><img src="https://github.com/lakshmiDRIP/DROP/blob/master/DRIP_Logo.gif?raw=true" width="100"></p>

Computation Support Library contains Cluster Support, Historical/Live Feed Processors, JSON Tranlators, Numerical Regression Tests, and Service Invocation Suite.


## Documentation

 |        Document         | Link |
 |-------------------------|------|
 | User Guide              | [*Latest*](https://github.com/lakshmiDRIP/DROP/blob/master/Docs/Internal/ComputationSupport/ComputationSupport_v5.11.pdf) [*Previous*](https://github.com/lakshmiDRIP/DROP/blob/master/Docs/Internal/ComputationSupport) |
 | API                     | [*Javadoc*](https://lakshmidrip.github.io/DROP/Javadoc/index.html)|


## Component Projects

 * *Feed* => Functionality to Load, Transform, and Compute Target Metrics across Feeds.
	* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/README.md) | 
	[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Afeed) }
 * *Historical* => Historical State Processing Utilities.
	* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/historical/README.md) | 
	[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Ahistorical) }
 * *JSON* => Implementation of the RFC-4627 Compliant JSON Encoder/Decoder (Parser).
	* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/json/README.md) | 
	[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Ajson) }
 * *Regression* => Regression Test Runs for Fixed Income, Numerical Analysis, and Spline Libraries.
	* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/regression/README.md) | 
	[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Aregression) }
 * *Service* => Environment, Product/Definition Containers, and hosts the Scenario/State Manipulation APIs.
	* { [**Home**](https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md) | 
	[**Project**](https://github.com/lakshmiDRIP/DROP/issues?q=is%3Aopen+is%3Aissue+label%3Aservice) }


## Coverage

 * Java 9 Garbage Collection Algorithms
	* Overview
	* Object Life Cycle
	* Garbage Collection Algorithms
	* Mark and Sweep
	* Concurrent Mark-Sweep (CMS) Garbage Collection
	* CMS GC Optimization Options
	* Serial Garbage Collection
	* Parallel Garbage Collection
	* G1 Garbage Collection
	* G1 Optimization Options
	* G1 Configuration Flags
	* G1 Logging Flags
 * Processes and Threads
	* Overview
	* Processes
	* Threads
 * Thread Objects
	* Overview
	* Defining and Starting a Thread
	* Pausing Execution with sleep
	* Interrupts
	* Supporting Interruption
	* The Interrupt Status Flag
	* Joins
 * Google Guice
	* Overview
	* Motivation
	* Direct Constructor Calls
	* Factories
	* Dependency Injection
	* Dependency Injection with Guice
 * Terraform
	* Abstract
	* Overview
	* Components
	* References
 * Terraform Use Cases
	* Overview
	* Heroku App Setup
	* Multi-tier Applications
	* Self-Service Clusters
	* Software Demos
	* Disposable Environments
	* Software Defined Networks
	* Resource Schedulers
	* Multi-Cloud Deployment
 * Terraform vs. Other Software
	* Overview
	* Terraform vs. Chef, Puppet, etc.
	* Terraform vs. Cloud Formation, Heat, etc.
	* Terraform vs. Boto, Fog, etc.
	* Terraform vs. Custom Solutions
 * Sample Terraform Configurations
	* Overview
	* Samples
	* Two-Tier AWS Architecture
	* Terraform-provider-aws/examples/two-tier/terraform-template.tform
	* Terraform-provider-aws/examples/two-tier/outputs.tf
	* Terraform-provider-aws/examples/two-tier/main.tf
	* Terraform-provider-aws/examples/two-tier/variables.tf
	* Cross Provider Example
	* Terraform-provider-aws/examples/count/variables.tf
	* Terraform-provider-aws/examples/count/outputs.tf
	* Terraform-provider-aws/examples/count/main.tf
	* Consul Example
	* Terraform-provider-consul/examples/kv/variables.tf
	* Terraform-provider-consul/examples/kv/main.tf
 * Kubernetes
	* Overview
	* Kubernetes Objects
	* Pods
	* ReplicaSets
	* Services
	* Volumes
	* Namespaces
	* ConfigMaps and Secrets
	* ReplicaSets
	* DeamonSets
	* Secrets
	* Managing Kubernetes Objects: Labels and Selectors
	* Managing Kubernetes Objects: Field Selectors
	* Replication Controllers and Deployments
	* Cluster API
	* Architecture
	* Kubernetes Control Plane
	* Kubernetes Node
	* Add-ons
	* Microservices
	* Kubernetes Persistence Architecture
	* References
 * Apache Kafka
	* Overview
	* Architecture
	* Connect API
	* Streams API
	* Version Compatibility
	* Performance
	* References


## DROP Specifications

 * Main                     => https://lakshmidrip.github.io/DROP/
 * Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * GitHub                   => https://github.com/lakshmiDRIP/DROP
 * Repo Layout Taxonomy     => https://lakshmidrip.github.io/DROP/Taxonomy.md
 * Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
