(ns noc.sketch)

(defmacro sketch-> [chapter]
  `{:init ~(symbol (str chapter "/init-state"))
    :setup ~(symbol (str chapter "/setup!"))
    :tick ~(symbol (str chapter "/tick"))
    :draw ~(symbol (str chapter "/draw!"))
    :size ~(symbol (str chapter "/size"))})
