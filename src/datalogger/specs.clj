(ns datalogger.specs
  (:require [clojure.spec.alpha :as s]))

(s/def ::ident (s/or :keyword keyword? :string string?))
(s/def ::level #{:trace :debug :info :warn :error})
(s/def ::pointer qualified-symbol?)
(s/def ::encode-key-fn boolean?)
(s/def ::decode-key-fn boolean?)
(s/def ::pretty boolean?)
(s/def ::mapper-config (s/keys :opt-un [::encode-key-fn ::decode-key-fn ::pretty]))
(s/def ::levels (s/map-of string? ::level))
(s/def ::mapper (s/or :config ::mapper-config :pointer ::pointer))
(s/def ::mask string?)
(s/def ::keys (s/coll-of ::ident :kind set?))
(s/def ::values (s/coll-of string? :kind set?))
(s/def ::masking (s/keys :opt-un [::mask ::keys ::values]))
(s/def ::config (s/keys :opt-un [::mapper ::levels ::masking]))