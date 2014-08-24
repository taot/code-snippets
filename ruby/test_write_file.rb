def run()
  File.open("output", 'w') do |file|
    file.puts("hello")
    file.puts("hello2")
  end
end

run
