/* eslint-disable no-template-curly-in-string */
/* eslint-disable react-hooks/exhaustive-deps */
import { FC, useEffect, useState } from "react";
import { initElements } from "./diagramsHandler";
import { ChainElement, ChainInfo, formChainFor } from "./chain";

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
    onValue: [
      {
        setText: {
          element: 'temperature',
          value: '${temperature} C'
        },
      },
      {
        setText: {
          element: 'humidity',
          value: '${humidity} C'
        },
      }
    ],
    when: {
      'data': {
        "${humidity} <= 0.5": [
          {
            hide: {
              element: 'spongebob'
            },
            show: {
              element: 'patrick'
            }
          }
        ],
        "${humidity} > 0.5": [
          {
            show: {
              element: 'spongebob'
            },
            hide: {
              element: 'patrick'
            }
          }
        ]
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
    return { elements };
  };

  useEffect(() => {
    init().catch(console.log);
  }, []);

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
    const interval = setInterval(() => {
      handleData({ temperature: Math.random(), humidity: Math.random() })
    }, 3000);
    return () => {
      clearInterval(interval);
    }
  }, [chain]);

  return (
    <>
      <div id={id}>
        <div
          className="drawio-diagram"
          data-diagram-url={diagramFilePath}
        ></div>
      </div>
      {/* <loader :open="loading" :timeout="-1" /> */}
      {/* <div v-if="loading" className="text-hidden">Keep this text in order to work.</div> */}
    </>
  );
};
