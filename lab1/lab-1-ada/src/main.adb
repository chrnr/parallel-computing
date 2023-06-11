with Ada.Text_IO;

procedure Main is
   type Thread_Index is range 1 .. 4;
   type Thread_Array is array (Thread_Index) of Boolean;
   can_stop : Thread_Array := (others => False);
   pragma Atomic(can_stop);

   task type break_thread;
   task type main_thread (Index : Thread_Index; Step : Long_Long_Integer);

   task body break_thread is
   begin
      for I in Thread_Index loop
         delay 30.0;
         can_stop(I) := True;
      end loop;
   end break_thread;

   task body main_thread is
      sum : Long_Long_Integer := 0;
      count : Long_Long_Integer := 0;
   begin
      loop
         sum := sum + Step;
         count := count + 1;
         exit when can_stop(Index);
      end loop;

      Ada.Text_IO.Put_Line("Thread " & Index'Img & ": Sum = " & sum'Img & ", Count = " & count'Img);
   end main_thread;

   b1 : break_thread;
   t1 : main_thread(1, 2);
   t2 : main_thread(2, 3);
   t3 : main_thread(3, 4);
   t4 : main_thread(4, 5);
begin
   null;
end Main;
