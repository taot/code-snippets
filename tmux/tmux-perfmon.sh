#! /usr/bin/env bash
tmux new-window -n perfmon 'mpstat -P ALL 1'
tmux split-window -h 'vmstat 1'
tmux select-pane -t 0
tmux split-window -v 'nicstat 1'
tmux select-pane -t 2
tmux split-window -v
#tmux select-layout even-horizontal
#tmux set-window-option synchronize-panes
