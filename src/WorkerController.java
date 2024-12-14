import java.util.ArrayList;

//class that handles CRUD operations for workers
public class WorkerController {

    private static WorkerController instance;

    private ArrayList<Worker> workers = DataStore.loadWorkers();


    //singleton
    public static WorkerController getInstance() {
        if (instance == null) {
            instance = new WorkerController();
        }
        return instance;
    }


    public ArrayList<Worker> getWorkers() {
        return workers;
    }
    public void addWorker(Worker worker) {
        workers.add(worker);
        DataStore.saveWorkers(workers);
        System.out.println("Worker added successfully.");
    }

    // Update a worker by name
    public boolean updateWorker(String name, Worker updatedWorker) {
        for (int i = 0; i < workers.size(); i++) {
            if (workers.get(i).getName().equals(name)) {
                workers.set(i, updatedWorker);
                DataStore.saveWorkers(workers);
                System.out.println("Worker updated successfully.");
                return true;
            }
        }
        System.out.println("Worker not found.");
        return false;
    }

    // Delete a worker by name
    public boolean deleteWorker(String name) {
        for (Worker worker : workers) {
            if (worker.getName().equals(name)) {
                workers.remove(worker);
                DataStore.saveWorkers(workers);
                System.out.println("Worker deleted successfully.");
                return true;
            }
        }
        System.out.println("Worker not found.");
        return false;
    }
}
