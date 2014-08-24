def run()
  IO.popen('git diff --cached', 'r') do |io|
    #io.puts input
    #io.close_write
    lines = io.readlines
    for l in lines do
      if l.start_with?("diff ")
        parts = l.split(" ")
        puts "svn " + parts[2]
      else
        puts l
      end
    end
  end
end

run
