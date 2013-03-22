#! /usr/bin/env bash
tmux new-window -n Hadoop 'ssh ArchLinux1'
tmux split-window -h 'ssh ArchLinux2'
tmux split-window -h 'ssh ArchLinux3'
tmux select-layout even-horizontal
tmux select-pane -t 0
tmux set-window-option synchronize-panes
