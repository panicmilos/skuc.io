/* eslint-disable no-template-curly-in-string */
/* eslint-disable react-hooks/exhaustive-deps */
import { FC, useEffect, useState } from "react";
import { initElements } from "./diagramsHandler";
import { ChainElement, ChainInfo, formChainFor } from "./chain";
import { EventsSocketIoClient } from "./EventsSocketIoClient";

const round = (n: number) => Math.round(n * 100) / 100;
const halfChance = () => Math.random() >= 0.5;

const createDataInterval = (handleData: (data: any) => any) => {
  return setInterval(() => {
    // handleData({ value: round(Math.random() * 100), deviceType: 'temperature', type: 'value' });
    // handleData({ value: round(Math.random() * 100), deviceType: 'humidity', type: 'value' });
    // handleData({ value: round(Math.random() * 100), deviceType: 'co2', type: 'value' });
    // handleData({ value: round(Math.random() * 100), deviceType: 'movement', type: 'value' });
    // handleData({ value: round(Math.random() * 100), deviceType: 'sound', type: 'value' });
    // handleData({ value: round(Math.random() * 100), deviceType: 'bathroom', type: 'value' });
    // handleData({ value: round(Math.random() * 100), deviceType: 'pet', type: 'value' });
    
    // handleData({ value: halfChance() ? 'TemperatureTooHot' : 'TemperatureTooCold', deviceType: 'temperature', type: 'status' });
    // handleData({ value: halfChance() ? 'WindowsOpened' : 'WindowsClosed', deviceType: 'windows', type: 'status' });
    // handleData({ value: halfChance() ? 'SprinklersOn' : 'SprinklersOff', deviceType: 'sprinkler', type: 'status' });
    // handleData({ value: halfChance() ? 'BoilerOn' : 'BoilerOff', deviceType: 'boiler', type: 'status' });
    // handleData({ value: halfChance() ? 'LightsOn' : 'LightsOff', deviceType: 'lights', type: 'status' });
    // handleData({ value: halfChance() ? 'WashingOn' : 'WashingOff', deviceType: 'washing', type: 'status' });
    // handleData({ value: halfChance() ? 'HeatingOn' : 'HeatingOff', deviceType: 'heating', type: 'status' });
  }, 3000);
}

type Props = {
  groupId: string;
  diagramFilePath: string;
  id?: string;
  onInit?: (chainElements: ChainElement[]) => any;
};

export const Diagram: FC<Props> = ({
  groupId,
  diagramFilePath,
  id = "1",
  onInit = () => {},
}) => {
  const metadata: ChainInfo = {
    onValue: [],
    when: {
      'data': {
        // VALUES
        // TEMPERATURE
        "'${deviceType}' === 'temperature' && '${type}' === 'value'": [
          {
            setText: {
              element: 'temperature',
              value: '${value} °C'
            },
          },
        ],
        // HUMIDITY
        "'${deviceType}' === 'humidity' && '${type}' === 'value'": [
          {
            setText: {
              element: 'humidity',
              value: '${value} %'
            },
          },
        ],
        // CO2
        "'${deviceType}' === 'co2' && '${type}' === 'value'": [
          {
            setText: {
              element: 'co2',
              value: '${value} %'
            },
          },
        ],
        // MOVEMENT
        "'${deviceType}' === 'movement' && '${type}' === 'value'": [
          {
            setText: {
              element: 'movement',
              value: '${value} %'
            },
          },
        ],
        // SOUND
        "'${deviceType}' === 'sound' && '${type}' === 'value'": [
          {
            setText: {
              element: 'sound',
              value: '${value} db'
            },
          },
        ],
        // PET
        "'${deviceType}' === 'pet' && '${type}' === 'value'": [
          {
            setText: {
              element: 'petdistance',
              value: '${value} m'
            },
          },
        ],
        // BATHROOM PRESENCE
        "'${deviceType}' === 'bathroom' && '${type}' === 'value'": [
          {
            setText: {
              element: 'bathroom',
              value: '${value} %'
            },
          },
        ],
        // STATUSES
        // TEMPERATURE
        "'${deviceType}' === 'temperature' && '${type}' === 'status' && '${value}' === 'TemperatureTooHot'": [
          {
            hide: {
              element: 'cold'
            },
            show: {
              element: 'hot'
            }
          }
        ],
        "'${deviceType}' === 'temperature' && '${type}' === 'status' && '${value}' === 'TemperatureTooCold'": [
          {
            show: {
              element: 'cold'
            },
            hide: {
              element: 'hot'
            }
          }
        ],
        // WINDOWS
        "'${deviceType}' === 'windows' && '${type}' === 'status' && '${value}' === 'WindowsOpened'": [
          {
            show: {
              element: 'windowsopen'
            },
            hide: {
              element: 'windowsclosed'
            }
          }
        ],
        "'${deviceType}' === 'windows' && '${type}' === 'status' && '${value}' === 'WindowsClosed'": [
          {
            hide: {
              element: 'windowsopen'
            },
            show: {
              element: 'windowsclosed'
            }
          }
        ],
        // AC
        "'${deviceType}' === 'ac' && '${type}' === 'status' && '${value}' === 'ACOn'": [
          {
            setText: {
              element: 'ac',
              value: 'On'
            },
          }
        ],
        "'${deviceType}' === 'ac' && '${type}' === 'status' && '${value}' === 'ACOff'": [
          {
            setText: {
              element: 'ac',
              value: 'Off'
            },
          }
        ],
        // SPRINKLERS
        "'${deviceType}' === 'sprinkler' && '${type}' === 'status' && '${value}' === 'SprinklersOn'": [
          {
            show: {
              element: 'sprinkleron'
            },
            hide: {
              element: 'sprinkleroff'
            }
          }
        ],
        "'${deviceType}' === 'sprinkler' && '${type}' === 'status' && '${value}' === 'SprinklersOff'": [
          {
            hide: {
              element: 'sprinkleron'
            },
            show: {
              element: 'sprinkleroff'
            }
          }
        ],
        // CAMERA
        "'${deviceType}' === 'camera' && '${type}' === 'status' && '${value}' === 'CameraActivity'": [
          {
            setText: {
              element: 'camera',
              value: 'Activity'
            },
          }
        ],
        "'${deviceType}' === 'camera' && '${type}' === 'status' && '${value}' === 'CameraNoActivity'": [
          {
            setText: {
              element: 'camera',
              value: 'No activity'
            },
          }
        ],
        "'${deviceType}' === 'camera' && '${type}' === 'status' && '${value}' === 'CameraRecording'": [
          {
            setText: {
              element: 'camera',
              value: 'Recording'
            },
          }
        ],
        "'${deviceType}' === 'camera' && '${type}' === 'status' && '${value}' === 'CameraNotRecording'": [
          {
            setText: {
              element: 'camera',
              value: 'Not recording'
            },
          }
        ],
        // HEATING
        "'${deviceType}' === 'heating' && '${type}' === 'status' && '${value}' === 'HeatingOn'": [
          {
            setText: {
              element: 'heating',
              value: 'On'
            },
          }
        ],
        "'${deviceType}' === 'heating' && '${type}' === 'status' && '${value}' === 'HeatingOff'": [
          {
            setText: {
              element: 'heating',
              value: 'Off'
            },
          }
        ],
        // PET
        "'${deviceType}' === 'pet' && '${type}' === 'status' && ('${value}' === 'PotentialPetMissing' || '${value}' === 'PotentialPetComeback')": [
          {
            show: {
              element: 'dog2'
            },
            hide: {
              element: 'dog1'
            }
          },
          {
            hide: {
              element: 'dog3'
            }
          }
        ],
        "'${deviceType}' === 'pet' && '${type}' === 'status' && '${value}' === 'PetMissing'": [
          {
            show: {
              element: 'dog3'
            },
            hide: {
              element: 'dog1'
            }
          },
          {
            hide: {
              element: 'dog2'
            }
          }
        ],
        "'${deviceType}' === 'pet' && '${type}' === 'status' && ('${value}' === 'PetComeback' || '${value}' === 'PetAtHome') ": [
          {
            show: {
              element: 'dog1'
            },
            hide: {
              element: 'dog2'
            }
          },
          {
            hide: {
              element: 'dog3'
            }
          }
        ],
        // BOILER
        "'${deviceType}' === 'boiler' && '${type}' === 'status' && '${value}' === 'BoilerOn'": [
          {
            setText: {
              element: 'boiler',
              value: 'On'
            },
          }
        ],
        "'${deviceType}' === 'boiler' && '${type}' === 'status' && '${value}' === 'BoilerOff'": [
          {
            setText: {
              element: 'boiler',
              value: 'Off'
            },
          }
        ],
        // LIGHTS
        "'${deviceType}' === 'lights' && '${type}' === 'status' && '${value}' === 'LightsOn'": [
          {
            setText: {
              element: 'lights',
              value: 'On'
            },
          }
        ],
        "'${deviceType}' === 'lights' && '${type}' === 'status' && '${value}' === 'LightsOff'": [
          {
            setText: {
              element: 'lights',
              value: 'Off'
            },
          }
        ],
        // WASHING
        "'${deviceType}' === 'washing' && '${type}' === 'status' && '${value}' === 'WashingOn'": [
          {
            setText: {
              element: 'washing',
              value: 'On'
            },
          }
        ],
        "'${deviceType}' === 'washing' && '${type}' === 'status' && '${value}' === 'WashingOff'": [
          {
            setText: {
              element: 'washing',
              value: 'Off'
            },
          }
        ],
      }
    }
  };

  const [chain, setChain] = useState<ChainElement[]>([]);
  const [queue] = useState<any[]>([]);

  const init = async () => {
    const { elements } = await initElements({
      id,
      idPrefix: "$element_",
      onClick: (element: any) => {
        console.log(element);
        chain.forEach((c) => c("click", element));
      },
    } as any);
    const chainElements = formChainFor([metadata], elements);
    onInit(chainElements);
    setChain(chainElements);
    return { elements, chain: chainElements };
  };

  useEffect(() => {
    init()
      .then(({ chain }) => {
        setChain(chain);
      })
      .catch(console.log);
  }, []);

  useEffect(() => {
    if(!chain.length) return;
    const client = new EventsSocketIoClient(groupId);
    client.onEvent(e => {
      console.log(e);
      if(e.type === "ValueReceived") {
        const { value, deviceType } = e;
        handleData({ value, deviceType, type: 'value' });
      } else if(e.type === "StatusReceived") {
        const { value, deviceType } = e;
        handleData({ value, deviceType, type: 'status' });
      } 
    });
  }, [chain]);

  const handleData = (data: any) => {
    if(data)
      queue.unshift(data);
    if(!chain.length) return;
    while(queue.length) {
      const d = queue.pop();
      chain.forEach(c => c('data', d));
    }
  }

  useEffect(() => {
    const interval = createDataInterval(handleData);
    return () => {
      clearInterval(interval);
    }
  }, [chain]);

  return (
    <div id={id} style={{ width: '100%', padding: '0 25vw', boxSizing: "border-box", }}>
      <div
        className="drawio-diagram"
        data-diagram-url={diagramFilePath}
      ></div>
    </div>
  );
};
