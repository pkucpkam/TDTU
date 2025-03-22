using UnityEngine;

namespace Chapter.Observer
{
    public class BikeController : Subject
    {
        public bool IsTurboOn
        {
            get; private set;
        }

        public float CurrentHealth
        {
            get { return health; } 
            
        }
        
        private bool _isEngineOn;
        private HUDController _hudController;
        private CameraController _cameraController;
        [SerializeField] private float health = 100.0f;
        
        void Awake()
        {
            _hudController = 
                gameObject.AddComponent<HUDController>();
            
            _cameraController = 
                (CameraController) 
                FindObjectOfType(typeof(CameraController));
        }

        private void Start()
        {
            StartEngine();
        }

        void OnEnable()
        {
            if (_hudController) 
                Attach(_hudController);
            
            if (_cameraController) 
                Attach(_cameraController);
        }
        
        void OnDisable()
        {
            if (_hudController) 
                Detach(_hudController);
            
            if (_cameraController) 
                Detach(_cameraController);
        }

        private void StartEngine()
        {
            _isEngineOn = true;
            
            NotifyObservers();
        }
        
        public void ToggleTurbo()
        {
            if (_isEngineOn) 
                IsTurboOn = !IsTurboOn;
            
            NotifyObservers();
        }
        
        public void TakeDamage(float amount)
        {
            health -= amount;
            IsTurboOn = false;
            
            NotifyObservers();
            
            if (health < 0)
                Destroy(gameObject);
        }
    }
}

namespace Chapter.State
{
    public class BikeStateContext
    {
        public IBikeState CurrentState
        {
            get; set;
        }
        
        private readonly BikeController _bikeController;

        public BikeStateContext(BikeController bikeController)
        {
            _bikeController = bikeController;
        }

        public void Transition()
        {
            CurrentState.Handle(_bikeController);
        }
        
        public void Transition(IBikeState state)
        {
            CurrentState = state;
            CurrentState.Handle(_bikeController);
        }
    }
}
using UnityEngine;
using System.Collections;

namespace Chapter.Strategy {
    public class BoppingManeuver : 
        MonoBehaviour, IManeuverBehaviour { 
        
        public void Maneuver(Drone drone) {
            StartCoroutine(Bopple(drone));
        }

        IEnumerator Bopple(Drone drone)
        {
            float time;
            bool isReverse = false;
            float speed = drone.speed;
            Vector3 startPosition = drone.transform.position;
            Vector3 endPosition = startPosition;
            endPosition.y = drone.maxHeight;

            while (true) {
                time = 0;
                Vector3 start = drone.transform.position;
                Vector3 end = 
                    (isReverse) ? startPosition : endPosition;

                while (time < speed) {
                    drone.transform.position = 
                        Vector3.Lerp(start, end, time / speed);
                    time += Time.deltaTime;
                    yield return null;
                }

                yield return new WaitForSeconds(1);
                isReverse = !isReverse;
            }
        }
    }
}
using UnityEngine;

namespace Chapter.Observer
{
    public class CameraController : Observer
    {
        private bool _isTurboOn;
        private Vector3 _initialPosition;
        private float _shakeMagnitude = 0.1f;
        private BikeController _bikeController;
        
        void OnEnable()
        {
            _initialPosition = 
                gameObject.transform.localPosition;
        }
        
        void Update()
        {
            if (_isTurboOn)
            {
                gameObject.transform.localPosition =
                    _initialPosition + 
                    (Random.insideUnitSphere * _shakeMagnitude);
            }
            else
            {
                gameObject.transform.
                    localPosition = _initialPosition;
            }
        }

        public override void Notify(Subject subject)
        {
            if (!_bikeController)
                _bikeController =
                    subject.GetComponent<BikeController>();

            if (_bikeController)
                _isTurboOn = _bikeController.IsTurboOn;
        }
    }
}

using UnityEngine;

namespace Chapter.Observer
{
    public class ClientObserver : MonoBehaviour
    {
        private BikeController _bikeController;
        
        void Start()
        {
            _bikeController = 
                (BikeController) 
                FindObjectOfType(typeof(BikeController));
        }
        
        void OnGUI()
        {
            if (GUILayout.Button("Damage Bike"))
                if (_bikeController) 
                    _bikeController.TakeDamage(15.0f);

            if (GUILayout.Button("Toggle Turbo"))
                if (_bikeController) 
                    _bikeController.ToggleTurbo();
        }
    }
}
using UnityEngine;

namespace Chapter.State
{
    public class ClientState : MonoBehaviour
    {
        private BikeController _bikeController;

        void Start()
        {
            _bikeController =
                (BikeController)
                FindObjectOfType(typeof(BikeController));
        }
        
        void OnGUI() 
        {
            if (GUILayout.Button("Start Bike"))
                _bikeController.StartBike();
            
            if (GUILayout.Button("Turn Left"))
                _bikeController.Turn(Direction.Left);
            
            if (GUILayout.Button("Turn Right"))
                _bikeController.Turn(Direction.Right);
            
            if (GUILayout.Button("Stop Bike"))
                _bikeController.StopBike();
        }
    }
}
using UnityEngine;
using System.Collections.Generic;

namespace Chapter.Strategy {
    public class ClientStrategy : MonoBehaviour {
        
        private GameObject _drone;
        private List<IManeuverBehaviour> 
            _components = new List<IManeuverBehaviour>();
        
        private void SpawnDrone() {
            _drone = 
                GameObject.CreatePrimitive(PrimitiveType.Cube);
            
            _drone.AddComponent<Drone>();
            
            _drone.transform.position = 
                Random.insideUnitSphere * 10;
            
            ApplyRandomStrategies();
        }

        private void ApplyRandomStrategies() {
            _components.Add(
                _drone.AddComponent<WeavingManeuver>());
            _components.Add(
                _drone.AddComponent<BoppingManeuver>());
            _components.Add(
                _drone.AddComponent<FallbackManeuver>());
            
            int index = Random.Range(0, _components.Count);
            
            _drone.GetComponent<Drone>().
                ApplyStrategy(_components[index]);
        }
        
        void OnGUI() {
            if (GUILayout.Button("Spawn Drone")) {
                SpawnDrone();
            }
        }
    }
}
namespace Chapter.Command
{
    public abstract class Command
    {
        public abstract void Execute();
    }
}
namespace Chapter.State
{
    public enum Direction
    {
        Left = -1,
        Right = 1
    }
}
using UnityEngine;

namespace Chapter.Strategy {
    public class Drone : MonoBehaviour {
        
        // Ray parameters
        private RaycastHit _hit;
        private Vector3 _rayDirection;
        private float _rayAngle = -45.0f;
        private float _rayDistance = 15.0f;

        // Movement parameters
        public float speed = 1.0f;
        public float maxHeight = 5.0f;
        public float weavingDistance = 1.5f;
        public float fallbackDistance = 20.0f;

        void Start() {
            _rayDirection = 
                transform.TransformDirection(Vector3.back) 
                * _rayDistance;
            
            _rayDirection = 
                Quaternion.Euler(_rayAngle, 0.0f, 0f) 
                * _rayDirection;
        }

        public void ApplyStrategy(IManeuverBehaviour strategy) {
            strategy.Maneuver(this);
        }

        void Update() {
            Debug.DrawRay(transform.position, 
                _rayDirection, Color.blue);
            
            if (Physics.Raycast(
                transform.position,
                _rayDirection, out _hit, _rayDistance)) {
                
                if (_hit.collider) {
                    Debug.DrawRay(
                        transform.position, 
                        _rayDirection, Color.green);
                }
            }
        }
    }
}
using UnityEngine;
using System.Collections;

namespace Chapter.Strategy
{
    public class FallbackManeuver : 
        MonoBehaviour, IManeuverBehaviour {
        
        public void Maneuver(Drone drone) {
            StartCoroutine(Fallback(drone));
        }

        IEnumerator Fallback(Drone drone)
        {
            float time = 0;
            float speed = drone.speed;
            Vector3 startPosition = drone.transform.position;
            Vector3 endPosition = startPosition;
            endPosition.z = drone.fallbackDistance;

            while (time < speed)
            {
                drone.transform.position = 
                    Vector3.Lerp(
                        startPosition, endPosition, time / speed);
               
                time += Time.deltaTime;
                
                yield return null;
            }
        }
    }
}
using System;
using UnityEngine;
using UnityEngine.SceneManagement;

namespace Chapter.Singleton 
{
    public class GameManager : Singleton<GameManager> 
    {
        private DateTime _sessionStartTime;
        private DateTime _sessionEndTime;

        void Start() {
            // TODO:
            // - Load player save
            // - If no save, redirect player to registration scene
            // - Call backend and get daily challenge and rewards 

            _sessionStartTime = DateTime.Now;
            Debug.Log(
                "Game session start @: " + DateTime.Now);
        }
        
        void OnApplicationQuit() {
            _sessionEndTime = DateTime.Now;
            
            TimeSpan timeDifference = 
                _sessionEndTime.Subtract(_sessionStartTime);
            
            Debug.Log(
                "Game session ended @: " + DateTime.Now);
            Debug.Log(
                "Game session lasted: " + timeDifference);
        }

        void OnGUI() {
            if (GUILayout.Button("Next Scene")) {
                SceneManager.LoadScene(
                    SceneManager.GetActiveScene().buildIndex + 1);
            }
        }
    }
}

using UnityEngine;

namespace Chapter.Observer {
    public class HUDController : Observer {
        private bool _isTurboOn;
        private float _currentHealth;
        private BikeController _bikeController;
        
        void OnGUI() {
            GUILayout.BeginArea (
                new Rect (50,50,100,200));
           
            GUILayout.BeginHorizontal ("box");
            GUILayout.Label ("Health: " + _currentHealth);
            GUILayout.EndHorizontal ();

            if (_isTurboOn) {
                GUILayout.BeginHorizontal("box");
                GUILayout.Label("Turbo Activated!");
                GUILayout.EndHorizontal();
            }
            
            if (_currentHealth <= 50.0f) {
                GUILayout.BeginHorizontal("box");
                GUILayout.Label("WARNING: Low Health");
                GUILayout.EndHorizontal();
            }
            
            GUILayout.EndArea ();
        }

        public override void Notify(Subject subject) {
            if (!_bikeController)
                _bikeController = 
                    subject.GetComponent<BikeController>();
            
            if (_bikeController) {
                _isTurboOn = 
                    _bikeController.IsTurboOn;
                
                _currentHealth = 
                    _bikeController.CurrentHealth;
            }
        }
    }
}
namespace Chapter.State
{
    public interface IBikeState
    {
        void Handle(BikeController controller);
    }
}

namespace Chapter.Strategy
{
    public interface IManeuverBehaviour
    {
        void Maneuver(Drone drone);
    }
}

using UnityEngine;

namespace Chapter.Command
{
    public class InputHandler : MonoBehaviour
    {
        private Invoker _invoker;
        private bool _isReplaying;
        private bool _isRecording;
        private BikeController _bikeController;
        private Command _buttonA, _buttonD, _buttonW;
        
        void Start()
        {
            _invoker = gameObject.AddComponent<Invoker>();
            _bikeController = FindObjectOfType<BikeController>();

            _buttonA = new TurnLeft(_bikeController);
            _buttonD = new TurnRight(_bikeController);
            _buttonW = new ToggleTurbo(_bikeController);
        }

        void Update()
        {
            if (!_isReplaying && _isRecording)
            {
                if (Input.GetKeyUp(KeyCode.A)) 
                    _invoker.ExecuteCommand(_buttonA);
                
                if (Input.GetKeyUp(KeyCode.D)) 
                    _invoker.ExecuteCommand(_buttonD);
                
                if (Input.GetKeyUp(KeyCode.W)) 
                    _invoker.ExecuteCommand(_buttonW);
            }
        }
        
        void OnGUI()
        {
            if (GUILayout.Button("Start Recording"))
            {
                _bikeController.ResetPosition();
                _isReplaying = false;
                _isRecording = true;
                _invoker.Record();
            }
            
            if (GUILayout.Button("Stop Recording"))
            {
                _bikeController.ResetPosition();
                _isRecording = false;
            }

            if (!_isRecording)
            {
                if (GUILayout.Button("Start Replay"))
                {
                    _bikeController.ResetPosition();
                    _isRecording = false;
                    _isReplaying = true;
                    _invoker.Replay();
                }
            }
        }
    }
}

using UnityEngine;
using System.Linq;
using System.Collections.Generic;

namespace Chapter.Command
{
    class Invoker : MonoBehaviour
    {
        private bool _isRecording;
        private bool _isReplaying;
        private float _replayTime;
        private float _recordingTime;
        private SortedList<float, Command> _recordedCommands = 
            new SortedList<float, Command>();

        public void ExecuteCommand(Command command)
        {
            command.Execute();
            
            if (_isRecording) 
                _recordedCommands.Add(_recordingTime, command);
            
            Debug.Log("Recorded Time: " + _recordingTime);
            Debug.Log("Recorded Command: " + command);
        }

        public void Record()
        {
            _recordingTime = 0.0f;
            _isRecording = true;
        }

        public void Replay()
        {
            _replayTime = 0.0f;
            _isReplaying = true;
            
            if (_recordedCommands.Count <= 0)
                Debug.LogError("No commands to replay!");
            
            _recordedCommands.Reverse();
        }
        
        void FixedUpdate()
        {
            if (_isRecording) 
                _recordingTime += Time.fixedDeltaTime;
            
            if (_isReplaying)
            {
                _replayTime += Time.fixedDeltaTime;

                if (_recordedCommands.Any()) 
                {
                    if (Mathf.Approximately(
                        _replayTime, _recordedCommands.Keys[0])) {

                        Debug.Log("Replay Time: " + _replayTime);
                        Debug.Log("Replay Command: " + 
                                  _recordedCommands.Values[0]);
                        
                        _recordedCommands.Values[0].Execute();
                        _recordedCommands.RemoveAt(0);
                    }
                }
                else
                {
                    _isReplaying = false;
                }
            }
        }
    }
}

using UnityEngine;

namespace Chapter.Observer
{
    public abstract class Observer : MonoBehaviour
    {
        public abstract void Notify(Subject subject);
    }
}

using UnityEngine;

namespace Chapter.Singleton
{
    public class  Singleton<T> : 
        MonoBehaviour where T : Component {
        
        private static T _instance;

        public static T Instance
        {
            get
            {
                if (_instance == null)
                {
                    _instance = FindObjectOfType<T>();

                    if (_instance == null)
                    {
                        GameObject obj = new GameObject();
                        obj.name = typeof(T).Name;
                        _instance = obj.AddComponent<T>();
                    }
                }

                return _instance;
            }
        }

        public virtual void Awake()
        {
            if (_instance == null)
            {
                _instance = this as T;
                DontDestroyOnLoad(gameObject);
            }
            else
            {
                Destroy(gameObject);
            }
        }
    }
}

using UnityEngine;
using System.Collections;

namespace Chapter.Observer
{
    public abstract class Subject : MonoBehaviour
    {
        private readonly 
            ArrayList _observers = new ArrayList();

        protected void Attach(Observer observer)
        {
            _observers.Add(observer);
        }

        protected void Detach(Observer observer)
        {
            _observers.Remove(observer);
        }

        protected void NotifyObservers()
        {
            foreach (Observer observer in _observers)
            {
                observer.Notify(this);
            }
        }
    }
}

namespace Chapter.Command
{
    public class ToggleTurbo : Command
    {
        private BikeController _controller;

        public ToggleTurbo(BikeController controller)
        {
            _controller = controller;
        }

        public override void Execute()
        {
            _controller.ToggleTurbo();
        }
    }
}
namespace Chapter.Command
{
    public class TurnLeft : Command
    {
        private BikeController _controller;

        public TurnLeft(BikeController controller)
        {
            _controller = controller;
        }

        public override void Execute()
        {
            _controller.Turn(BikeController.Direction.Left);
        }
    }
}
namespace Chapter.Command
{
    public class TurnRight : Command
    {
        private BikeController _controller;

        public TurnRight(BikeController controller)
        {
            _controller = controller;
        }

        public override void Execute()
        {
            _controller.Turn(BikeController.Direction.Right);
        }
    }
}
using UnityEngine;
using System.Collections;

namespace Chapter.Strategy {
    public class WeavingManeuver : 
        MonoBehaviour, IManeuverBehaviour { 
        
        public void Maneuver(Drone drone) {
            StartCoroutine(Weave(drone));
        }

        IEnumerator Weave(Drone drone) {
            float time;
            bool isReverse = false;
            float speed = drone.speed;
            Vector3 startPosition = drone.transform.position;
            Vector3 endPosition = startPosition;
            endPosition.x = drone.weavingDistance;

            while (true) {
                time = 0;
                Vector3 start = drone.transform.position;
                Vector3 end = 
                    (isReverse) ? startPosition : endPosition;

                while (time < speed) {
                    drone.transform.position = 
                        Vector3.Lerp(start, end, time / speed);
                    
                    time += Time.deltaTime;
                    
                    yield return null;
                }

                yield return new WaitForSeconds(1);
                isReverse = !isReverse;
            }
        }
    }
}