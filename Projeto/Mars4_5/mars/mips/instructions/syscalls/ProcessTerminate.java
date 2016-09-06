package mars.mips.instructions.syscalls;

import mars.ProcessingException;
import mars.ProgramStatement;
import mars.mips.hardware.RegisterFile;
import mars.pluginRJ.ProcessManager;
import mars.util.SystemIO;
import mars.pluginRJ.ProcessControlBlock;

public class ProcessTerminate extends AbstractSyscall{

	public ProcessTerminate() {
		super(21, "ProcessTerminate");
	}

	@Override
	public void simulate(ProgramStatement statement) throws ProcessingException {
		
		ProcessControlBlock pcb = ProcessManager.processTerminate();
		
		//se existe algum processo para ser executado, as informações será adicionada nos registradores
		if(pcb != null){
			for(int i = 0; i < 32; i++){
				RegisterFile.updateRegister(i, pcb.getValueReg(i));
			}
			RegisterFile.setProgramCounter(pcb.getPc());
			RegisterFile.setHi(pcb.getHi());
			RegisterFile.setLo(pcb.getLo());
			SystemIO.printString("\n\n********* Pocesso: p" + pcb.getPid() + " Entrando no processador:\n");
		}else{
			SystemIO.printString("Nao possui mais nenhum processo para ser executado! Finalizando...");
			//new SyscallExit();
		}
	}
}
