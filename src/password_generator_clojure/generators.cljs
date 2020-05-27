(ns password-generator-clojure.generators)

(defn lower-char []
  (rand-nth "abcdefghijklmnopqrstuvwxyz"))

(defn upper-char []
  (rand-nth "ABCDEFGHIJKLMNOPQRSTUVWXYZ"))

(defn numeric []
  (rand-int 10))

(defn special-char []
  (rand-nth "!#$%&()*+,-.:;<=>?@^_{|}~"))

(defn generate-password [length c? n? s?]
  (let [freq (/ length (count (filter true? [c? n? s?])))]
    (apply str (take length (shuffle
                              (mapcat (fn [x] ((juxt lower-char (when c? upper-char) (when n? numeric) (when s? special-char)))) (range freq)))))))