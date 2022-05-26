import { useEffect, useState } from "react";
import { BACKEND_API, CrudService } from "../imports";
import { Context, CreateContextReqest, UpdateContextRequest } from "../models";

export const CONTEXTS_SERVICE_ID = 'ContextsService';

export const useContextsService = (groupId: string) => {

  const [contextsService, setContextsService] = useState(new ContextsService(groupId));

  useEffect(() => {
    setContextsService(new ContextsService(groupId));
  }, [groupId]);

  return [contextsService];
}

export class ContextsService extends CrudService<Context, CreateContextReqest, UpdateContextRequest> {
  constructor(groupId: string) {
    super(CONTEXTS_SERVICE_ID, `${BACKEND_API}/groups/${groupId}/contexts`);
  }
}