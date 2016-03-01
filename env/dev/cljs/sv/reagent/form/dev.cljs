(ns ^:figwheel-no-load sv.reagent.form.dev
  (:require [figwheel.client :as figwheel :include-macros true]))

(enable-console-print!)

(figwheel/watch-and-reload
  :websocket-url "ws://localhost:3449/figwheel-ws"
  ;;:jsload-callback core/mount-root
  )
