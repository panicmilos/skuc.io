export type ChainInfo = {
  sensorId?: string;
  onValue?: ActionInfos[];
  when?: WhenInfo;
};

type ActionInfos =
  | {
      [key in AtomicAction]: ActionInfo;
    }
  | {};

type ActionInfo = {
  element: string;
};

type TextActionInfo = ActionInfo & {
  value: string;
};

type StyleActionInfo = ActionInfo & {
  value: { [key: string]: string };
};

type PathActionInfo = ActionInfo & {
  path: string;
};

type WhenType = "data" | "alarm" | "sensorStatus" | "click";

type WhenInfo =
  | {
      [key in WhenType]: {
        [key: string]: ActionInfos[];
      };
    }
  | {};

type AtomicAction = "hide" | "show" | "setStyle" | "setText" | "goTo";
type AtomicActionHandlers =
  | {
      [key in AtomicAction]: ActionHandler;
    }
  | {};
type ActionHandler = (
  actionInfo: ActionInfo,
  type: WhenType,
  data: any
) => void;

export type ChainElement = (type: WhenType, data: any) => any;

export const formChainFor = (chainInfos: ChainInfo[], elements: any): ChainElement[] => {
  const atomicActions = {
    hide: (actionInfo: ActionInfo, type: WhenType, data: any) => {
      elements[actionInfo.element]?.hide();
    },
    show: (actionInfo: ActionInfo, type: WhenType, data: any) => {
      elements[actionInfo.element]?.show();
    },
    setStyle: (actionInfo: StyleActionInfo, type: WhenType, data: any) => {
      Object.keys(actionInfo.value).forEach((key) => {
        elements[actionInfo.element]?.setStyle(key, actionInfo.value[key]);
      });
    },
    setText: (actionInfo: TextActionInfo, type: WhenType, data: any) => {
      elements[actionInfo.element]?.setText(() =>
        interpolate(actionInfo.value, data)
      );
    },
    goTo: (actionInfo: PathActionInfo, type: WhenType, data: any) => {
      window.location.href = actionInfo.path;
    },
  };
  return chainInfos.map((chainInfo) => {
    const forFilter = chainInfo.sensorId ? forSensor : forAny;
    return forFilter(
      chainInfo.sensorId ?? "",
      ...(chainInfo.onValue
        ? chainInfo.onValue.map((actionInfo) =>
            formAction(actionInfo, atomicActions)
          )
        : []),
      ...(chainInfo.when
        ? Object.keys(chainInfo.when).map((whenType) =>
            formWhen(whenType as WhenType, chainInfo.when ?? {}, atomicActions)
          )
        : [])
    );
  });
};

const formWhen = (
  whenType: WhenType,
  whenInfo: WhenInfo,
  atomicActions: AtomicActionHandlers
) => {
  return (type: WhenType, data: any) => {
    const whenInfoKey = whenType as keyof WhenInfo;

    Object.keys(whenInfo[whenInfoKey]).forEach((expression) => {
      const whenAtomicActions = whenInfo[whenInfoKey][
        expression
      ] as ActionInfos[];
      when(
        whenType,
        expression,
        ...whenAtomicActions.map((actionInfo) =>
          formAction(actionInfo, atomicActions)
        )
      )(type, data);
    });
  };
};

const formAction = (
  actionInfo: ActionInfos,
  atomicActions: AtomicActionHandlers
) => {
  return (type: WhenType, data: any) => {
    Object.keys(actionInfo).forEach((key: string) => {
      const actionHandler: ActionHandler =
        atomicActions[key as keyof AtomicActionHandlers];
      actionHandler(actionInfo[key as keyof ActionInfos], type, data);
    });
  };
};

// Filter by type of event and sensorId
const forSensor =
  (sensorId: string, ...successors: any[]) =>
  (type: WhenType, data: any) => {
    const typeConditions = {
      data: () => data?.sensor.id === sensorId,
      alarm: () => data?.sensor.id === sensorId,
      sensorStatus: () => data?.sensorId === sensorId,
      click: () => false,
    };
    if (typeConditions[type]()) {
      successors?.forEach((succ) => succ(type, data));
    }
  };

// Call all successors
const forAny =
  (_: any, ...successors: ChainElement[]) =>
  (type: WhenType, data: any) => {
    successors?.forEach((succ) => succ(type, data));
  };

// Replaces '${nested.property.path}' to property value from object { nested: { property: { path: 1 } } } to '1'
const interpolate = (str: string, obj: any) =>
  str.replace(/\${(.*?)}/g, (_, g) =>
    g.split(".").reduce((o: any, i: number) => o[i], obj)
  );

// Filter by type of event and expression
const when =
  (
    conditionType: WhenType,
    conditionExpression: string,
    ...successors: ChainElement[]
  ) =>
  (type: WhenType, data: any) => {
    if (conditionType === type) {
      // Interpolate values and evaluate the expression
      const matches = [...conditionExpression.matchAll(/exists\((.*?)\)/g)];
      matches.forEach(match => {
        conditionExpression = conditionExpression.replaceAll(match[0], `'\${${match[1]}}' != 'undefined'`);
      })
      // eslint-disable-next-line no-eval
      if (eval(interpolate(conditionExpression, data))) {
        successors?.forEach((succ) => succ(type, data));
      }
    }
  };
