(ns datalogger.provider
  (:import (org.slf4j.helpers NOPServiceProvider BasicMarkerFactory)
           (org.slf4j.bridge SLF4JBridgeHandler)
           (org.slf4j.spi SLF4JServiceProvider))
  (:gen-class
    :implements [SLF4JServiceProvider]
    :constructors {[] []}))

(set! *warn-on-reflection* true)

(defonce marker-factory (delay (BasicMarkerFactory.)))
(defonce logger-factory (delay ((requiring-resolve 'datalogger.loggers/data-logger-factory))))
(defonce mdc-adapter (delay (force (deref (requiring-resolve 'datalogger.context/mdc-adapter)))))

(defn -getLoggerFactory [^SLF4JServiceProvider this]
  (force logger-factory))

(defn -getMarkerFactory [^SLF4JServiceProvider this]
  (force marker-factory))

(defn -getMDCAdapter [^SLF4JServiceProvider this]
  (force mdc-adapter))

(defn -getRequesteApiVersion [^SLF4JServiceProvider this]
  NOPServiceProvider/REQUESTED_API_VERSION)

(defn -initialize [^SLF4JServiceProvider this]
  (SLF4JBridgeHandler/removeHandlersForRootLogger)
  (SLF4JBridgeHandler/install)
  (.getLoggerFactory this)
  (.getMarkerFactory this)
  (.getMDCAdapter this))